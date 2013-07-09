angular.module("smartRegistry.controllers")
    .controller("VideoController", ["$scope", function ($scope) {
         FAMILY_PLANNING = 1;
         ANTENATAL = 2;
         POST_PARTUM = 3;
         CHILD_WELLNESS = 4;

        $scope.navigationBridge = new ANMNavigationBridge();

        $scope.goBack = function () {
            $scope.navigationBridge.goBack();
        };

        $scope.videoCategories = [
            {
                id: FAMILY_PLANNING,
                title: "Family Planning",
                num_videos: 6,
                css_class: "icon-video-fp"
            },
            {
                id: ANTENATAL,
                title: "Antenatal",
                num_videos: 4,
                css_class: "icon-video-antenatal"
            },
            {
                id: POST_PARTUM,
                title: "Post Partum",
                num_videos: 1,
                css_class: "icon-video-pp"
            },
            {
                id: CHILD_WELLNESS,
                title: "Child Wellness",
                num_videos: 4,
                css_class: "icon-video-wellness"
            }
        ];

        var defaultCategoryId = FAMILY_PLANNING;
        $scope.currentCategoryId = defaultCategoryId;

        $scope.videoList = [
            {
                title: "Introduction to FP",
                time: "4.42",
                previewImageUrl: "",
                category: FAMILY_PLANNING
            },
            {
                title: "Condom Method",
                time: "4.42",
                previewImageUrl: "",
                category: FAMILY_PLANNING
            },
            {
                title: "OCP",
                time: "4.42",
                previewImageUrl: "",
                category: FAMILY_PLANNING
            },
            {
                title: "IUD",
                time: "4.42",
                previewImageUrl: "",
                category: FAMILY_PLANNING
            },
            {
                title: "Tubal Ligation",
                time: "4.42",
                previewImageUrl: "",
                category: FAMILY_PLANNING
            },
            {
                title: "Vasectomy",
                time: "4.42",
                previewImageUrl: "",
                category: FAMILY_PLANNING
            },
            {
                title: "ANC General",
                time: "4.42",
                previewImageUrl: "",
                category: ANTENATAL
            },
            {
                title: "IFA",
                time: "4.42",
                previewImageUrl: "",
                category: ANTENATAL
            },
            {
                title: "Birth Plan",
                time: "4.42",
                previewImageUrl: "",
                category: ANTENATAL
            },
            {
                title: "Danger Signs",
                time: "4.42",
                previewImageUrl: "",
                category: ANTENATAL
            },
            {
                title: "Introduction to PNC",
                time: "4.42",
                previewImageUrl: "",
                category: POST_PARTUM
            },
            {
                title: "Child Overview",
                time: "4.42",
                previewImageUrl: "",
                category: CHILD_WELLNESS
            },
            {
                title: "Diarrhea",
                time: "4.42",
                previewImageUrl: "",
                category: CHILD_WELLNESS
            },
            {
                title: "ARI",
                time: "4.42",
                previewImageUrl: "",
                category: CHILD_WELLNESS
            },
            {
                title: "Malnutrition",
                time: "4.42",
                previewImageUrl: "",
                category: CHILD_WELLNESS
            }
        ];

        $scope.setCurrentCategory = function(id) {
            $scope.currentCategoryId = id;
        };

        $scope.videoClicked = function(video_id) {
            alert("Clicked video '" + video_id + "' ..");
        };
    }]);