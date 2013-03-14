function CameraBridge() {
    var context = window.cameraContext;
    if (typeof context === "undefined" && typeof FakeCameraContext() !== "undefined") {
        context = new FakeCameraContext();
    }

    return {
        takePhoto: function () {
            context.takePhoto();
        }
    };
}

function FakeCameraContext() {
    return {
        takePhoto: function () {
            alert("launching camera app.");
        }
    }
}
