let darkmode = localStorage.getItem("darkmode");
const themeSwitch = document.getElementById("theme-toggle");

const enableDarkmode = () => {
  document.body.classList.add("darkmode");
  localStorage.setItem("darkmode", "active");
};

const disableDarkmode = () => {
  document.body.classList.remove("darkmode");
  localStorage.setItem("darkmode", null);
};

if (darkmode === "active") {
  enableDarkmode();
}

themeSwitch.addEventListener("click", () => {
  darkmode = localStorage.getItem("darkmode");
  darkmode !== "active" ? enableDarkmode() : disableDarkmode();
});

window.addEventListener("storage", (event) => {
  if (event.key) {
    const darkmode = localStorage.getItem("darkmode");
    if (darkmode === "active") {
      enableDarkmode();
    } else {
      disableDarkmode();
    }
  }
});

const user = {
  name: "Neiltion",
  age: 29,
};

localStorage.setItem("user", JSON.stringify(user));

const storedUser = JSON.parse(localStorage.getItem("user"));

console.log(storedUser);

localStorage.removeItem("user");
localStorage.clear();
