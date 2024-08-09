console.log("Admin Page");

document
  .querySelector("#image_file_input")
  .addEventListener("change", function (event) {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function () {
      document
        .querySelector("#preview_image")
        .setAttribute("src", reader.result);

      reader.readAsDataURL(file);
    };
  });
