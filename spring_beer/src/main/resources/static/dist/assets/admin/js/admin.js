(() => {
  var __getOwnPropNames = Object.getOwnPropertyNames;
  var __commonJS = (cb, mod) => function __require() {
    return mod || (0, cb[__getOwnPropNames(cb)[0]])((mod = { exports: {} }).exports, mod), mod.exports;
  };

  // assets/admin/css/style.css
  var require_style = __commonJS({
    "assets/admin/css/style.css"(exports, module) {
      module.exports = {};
    }
  });

  // assets/admin/js/components/dynamic_table/knp_spa.js
  var require_knp_spa = __commonJS({
    "assets/admin/js/components/dynamic_table/knp_spa.js"() {
      async function componentsDynamicTableKnpSpa() {
        const knpTableContainer = document.querySelector("#dynamic-knp-table");
        if (!knpTableContainer) {
          return;
        }
        const columnNameTranslations = {};
        for (const elem of knpTableContainer.querySelectorAll(".sortable")) {
          const propertyName = elem.dataset.propertyName;
          const translation = elem.title;
          columnNameTranslations[propertyName] = translation;
        }
        const fade = document.querySelector("#dynamic-knp-table .tear");
        fade.classList.remove("d-none");
        const response = await fetch(knpTableContainer.getAttribute("data-automplete-url"));
        if (!response.ok) {
          console.error("Cannot fetch table");
          return;
        }
        const data = await response.text();
        knpTableContainer.innerHTML = data;
        fade.classList.add("d-none");
        spaPagination();
        function linkSearchInput() {
          let knpSearchInput = document.getElementById("data-table-search");
          if (!knpSearchInput) {
            return;
          }
          let isInput = false;
          let inputTimeout = null;
          knpSearchInput.addEventListener("input", (e) => {
            if (isInput) {
              clearTimeout(inputTimeout);
            }
            isInput = true;
            inputTimeout = setTimeout(async () => {
              document.getElementById("search").value = knpSearchInput.value;
              isSearching = false;
              await handleDataTableLiveSearch(e);
              isInput = false;
            }, 150);
          });
          knpSearchInput.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
              document.querySelector("#filters-form").submit();
            }
            const wasArrowDownPressed = e.key === "ArrowDown";
            if (wasArrowDownPressed) {
              document.querySelector(".auto-complete-result")?.focus();
              return;
            }
            const wasEnterPressed = e.key === "Enter";
            if (!wasEnterPressed) {
              return;
            }
            knpSearchInput.blur(true);
          });
        }
        function spaPagination() {
          const links = document.querySelectorAll("#dynamic-knp-table a.page-link:not([disabled])");
          links.forEach((link) => {
            link.addEventListener("click", (e) => {
              e.preventDefault();
              const url = new URL(link.getAttribute("href"), window.location.origin);
              if (!url.pathname.endsWith("/page")) {
                url.pathname += `/page`;
              }
              const fade2 = document.querySelector("#dynamic-knp-table .tear");
              fade2.classList.remove("d-none");
              fetch(url.toString()).then((data2) => data2.text()).then((data2) => {
                fade2.classList.add("d-none");
                knpTableContainer.innerHTML = data2;
                spaPagination();
              });
              window.history.pushState(null, "", link.getAttribute("href").replace("/page", ""));
            });
          });
        }
        let isSearching = false;
        let isFocus = false;
        function handleBlurSearchBox() {
          const results = document.querySelector("#data-table-search-results-container");
          if (!results) {
            return;
          }
          checkFocus();
          setTimeout(() => {
            if (isFocus) {
              return;
            }
            results.setAttribute("hidden", "hidden");
          }, 0);
        }
        function checkFocus() {
          setTimeout(() => {
            if (!document.activeElement.closest(".search-box-container")) {
              isFocus = false;
            }
          }, 0);
        }
        async function handleDataTableLiveSearch(event) {
          if (isSearching) {
            return;
          }
          const resultsContainer = document.querySelector("#data-table-search-results-container");
          const results = resultsContainer?.querySelector("#data-table-search-results");
          const searchBox = document.querySelector("#data-table-search");
          if (!resultsContainer) {
            return;
          }
          isSearching = true;
          results.innerHTML = "";
          const target = event.target;
          const inputValue = target?.value;
          if (!inputValue) {
            resultsContainer.setAttribute("hidden", "hidden");
            isSearching = false;
            return;
          }
          const elemAutocompleteUrl = knpTableContainer.getAttribute("data-automplete-url");
          if (!elemAutocompleteUrl) {
            return;
          }
          const autoCompleteBaseUrl = new URL(elemAutocompleteUrl, window.location.origin);
          const autoCompleteSearchParams = new URLSearchParams(autoCompleteBaseUrl.search);
          const endpointUrlWithFilters = new URL(window.location.href, window.location.origin);
          const endpointSearchParams = new URLSearchParams(endpointUrlWithFilters.search);
          for (const [key, value] of endpointSearchParams.entries()) {
            autoCompleteSearchParams.set(key, value);
          }
          autoCompleteSearchParams.set("search", inputValue);
          autoCompleteBaseUrl.search = autoCompleteSearchParams.toString();
          const urlToSearch = autoCompleteBaseUrl.toString();
          const response2 = await fetch(urlToSearch, {
            method: "GET",
            headers: {
              "Content-Type": "application/json"
            }
          });
          const data2 = await response2.json();
          resultsContainer.querySelector("#current-results-count").textContent = data2.items.length;
          resultsContainer.querySelector("#total-results-count").textContent = data2.totalCount;
          results.innerHTML = "";
          for (const item of data2.items) {
            const value = item.value;
            const selectedFrom = value.toLowerCase().indexOf(inputValue.toLowerCase());
            const selectedTo = inputValue.length + selectedFrom;
            const nonMatchedFirst = value.substring(0, selectedFrom);
            const matched = value.substring(value.indexOf(inputValue), selectedTo);
            const nonMatchedLast = value.substring(selectedTo);
            const detectedMatch = `${nonMatchedFirst}<strong class="text-info">${matched}</strong>${nonMatchedLast}`;
            results.innerHTML += `
                <p>
                    <button 
                        type="button" 
                        class="auto-complete-result btn text-start w-100 h-100 p-2" 
                        data-hint="${item.value}"
                    >
                        <span class="badge text-bg-secondary">
                            ${columnNameTranslations[item.propertyName]}
                        </span>
                        <span>
                            ${detectedMatch}
                        </span>
                    </button>
                </p>
            `.trim();
          }
          const autoCompleteResults = results.querySelectorAll(".auto-complete-result");
          for (const [index, elem] of autoCompleteResults.entries()) {
            elem.addEventListener("focus", () => {
              isFocus = true;
            });
            elem.addEventListener("blur", handleBlurSearchBox);
            elem.addEventListener("keydown", (event2) => {
              const key = event2.key;
              const wasPressedArrowUp = key === "ArrowUp";
              const wasPressedArrowDown = key === "ArrowDown";
              if (wasPressedArrowUp) {
                if (index === 0) {
                  searchBox.focus();
                }
                if (index > 0) {
                  autoCompleteResults[index - 1].focus();
                }
              }
              if (wasPressedArrowDown) {
                if (index < autoCompleteResults.length - 1) {
                  autoCompleteResults[index + 1].focus();
                }
              }
            });
            elem.addEventListener("click", () => {
              isFocus = false;
              elem.blur();
              target.value = elem.dataset.hint;
              results.innerHTML = "";
              document.getElementById("search").value = elem.dataset.hint;
              document.querySelector("#filters-form").submit();
            });
          }
          resultsContainer.removeAttribute("hidden");
          isSearching = false;
        }
        linkSearchInput();
        spaPagination();
      }
      componentsDynamicTableKnpSpa();
    }
  });

  // assets/admin/js/components/notifications/notifications.index.js
  var require_notifications_index = __commonJS({
    "assets/admin/js/components/notifications/notifications.index.js"() {
      document.querySelectorAll(".notification").forEach((elem) => {
        const content = {
          message: elem.dataset.message || "",
          title: elem.dataset.title || "",
          icon: "fa-solid fa-bell",
          url: elem.dataset.url || "",
          target: elem.dataset.target || "_self"
        };
        $.notify(content, {
          type: elem.dataset.type || "info",
          placement: {
            from: elem.dataset.placementFrom || "top",
            align: elem.dataset.placementAlign || "center"
          },
          time: elem.dataset.time,
          delay: 0
        });
      });
    }
  });

  // assets/admin/js/admin.js
  require_style();
  require_knp_spa();
  require_notifications_index();
})();
