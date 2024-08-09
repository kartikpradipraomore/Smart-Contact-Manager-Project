console.log("loaded");

document.addEventListener("DOMContentLoaded", () => {
  let currentTheme = getTheme();
  console.log(currentTheme);

  applyTheme(currentTheme); // Apply the theme on page load

  const changeThemeButton = document.querySelector("#theme_change_button");
  changeThemeButton.addEventListener("click", () => {
    const oldTheme = currentTheme;
    console.log("clicked");
    if (currentTheme === "dark") {
      currentTheme = "light";
    } else {
      currentTheme = "dark";
    }

    setTheme(currentTheme);

    document.querySelector("html").classList.remove(oldTheme);
    document.querySelector("html").classList.add(currentTheme);

    changeThemeButton.querySelector("span").textContent =
      currentTheme === "light" ? "Dark" : "Light";
  });
});

function applyTheme(theme) {
  document.querySelector("html").classList.add(theme);
  const changeThemeButton = document.querySelector("#theme_change_button");
  if (changeThemeButton) {
    changeThemeButton.querySelector("span").textContent =
      theme === "light" ? "Dark" : "Light";
  }
}

function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

function getTheme() {
  let theme = localStorage.getItem("theme");
  if (theme) return theme;
  else return "light";
}
