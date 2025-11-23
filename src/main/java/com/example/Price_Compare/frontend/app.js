/* ===============================
     API URL (backend)
================================*/
const API = "http://localhost:8080/api/products";

/* ===============================
     FETCH PRODUCTS (server-side search)
================================*/
async function fetchProducts(query = "") {
  try {
    const u = `${API}?q=${encodeURIComponent(query || "")}`;
    const res = await fetch(u);
    if (!res.ok) throw new Error("Failed to fetch products");
    return await res.json();
  } catch (e) {
    console.error(e);
    return [];
  }
}

/* ===============================
    LOWEST PRICE FUNCTION
================================*/
function getLowest(prices) {
  if (!prices || !prices.length) return Infinity;
  return Math.min(...prices);
}

/* ===============================
    RENDER PRODUCTS
================================*/
function renderProducts(products) {
  const cont = document.getElementById("products");
  cont.innerHTML = "";

  if (!products || products.length === 0) {
    cont.innerHTML = "<p style='padding:20px;text-align:center'>No products found</p>";
    return;
  }

  products.forEach(p => {
    const lowest = getLowest(p.prices || [Infinity]);
    const card = document.createElement("div");
    card.className = "card";

    const amazonSearch = `https://www.amazon.in/s?k=${encodeURIComponent(p.name)}`;
    const flipkartSearch = `https://www.flipkart.com/search?q=${encodeURIComponent(p.name)}`;
    const cromaSearch = `https://www.croma.com/search/?text=${encodeURIComponent(p.name)}`;

    card.innerHTML = `
      <img src="${p.image || 'https://via.placeholder.com/150'}" alt="${p.name}">
      <h3>${p.name}</h3>
      <p><strong>${p.brand}</strong> — ${p.model || ''}</p>
      <p class="stars">Rating: ${p.rating ?? '-'} ★</p>

      <div class="price-list">
        <div class="price-item">
            <span class="platform">Amazon</span>
            <span class="amount ${p.prices && p.prices[0]===lowest ? 'lowest' : ''}">₹${p.prices ? p.prices[0] : '-'}</span>
            <a class="visit-btn" href="${amazonSearch}" target="_blank">Visit</a>
        </div>

        <div class="price-item">
            <span class="platform">Flipkart</span>
            <span class="amount ${p.prices && p.prices[1]===lowest ? 'lowest' : ''}">₹${p.prices ? p.prices[1] : '-'}</span>
            <a class="visit-btn" href="${flipkartSearch}" target="_blank">Visit</a>
        </div>

        <div class="price-item">
            <span class="platform">Croma</span>
            <span class="amount ${p.prices && p.prices[2]===lowest ? 'lowest' : ''}">₹${p.prices ? p.prices[2] : '-'}</span>
            <a class="visit-btn" href="${cromaSearch}" target="_blank">Visit</a>
        </div>
      </div>
    `;
    cont.appendChild(card);
  });
}

/* ===============================
    SEARCH + SORT (server side search)
================================*/
async function loadAndRender() {
  const q = document.getElementById("searchInput").value.trim();
  const products = await fetchProducts(q);
  window._productCache = products;
  applyFiltersAndRender(); // sorting still done client-side
}

function applyFiltersAndRender() {
  let list = (window._productCache || []).slice();

  const sort = document.getElementById("sortSelect").value;

  if (sort === "low") {
    list.sort((a, b) => getLowest(a.prices) - getLowest(b.prices));
  } else if (sort === "high") {
    list.sort((a, b) => getLowest(b.prices) - getLowest(a.prices));
  } else if (sort === "rating") {
    list.sort((a, b) => (b.rating || 0) - (a.rating || 0));
  }

  renderProducts(list);
}

/* ===============================
    EVENT LISTENERS
================================*/
document.getElementById("searchBtn").addEventListener("click", loadAndRender);
// optional: support pressing Enter in input
document.getElementById("searchInput").addEventListener("keydown", (e) => {
  if (e.key === "Enter") loadAndRender();
});
document.getElementById("sortSelect").addEventListener("change", applyFiltersAndRender);
window.addEventListener("load", loadAndRender);
