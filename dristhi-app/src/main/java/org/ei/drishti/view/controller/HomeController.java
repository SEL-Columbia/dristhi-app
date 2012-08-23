package org.ei.drishti.view.controller;

public class HomeController {
    private UpdateController updateController;

    public HomeController(UpdateController updateController) {
        this.updateController = updateController;
    }

    public void pageHasFinishedLoading() {
        updateController.pageHasFinishedLoading();
    }
}
