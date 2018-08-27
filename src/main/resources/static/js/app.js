var todoListApp = angular.module('app', []);

todoListApp.controller('appController', function PhoneListController($scope, $http) {
  var URL = '/schedule';

  var alertMessage = function(reason) {
    alert(reason.data.message);
  };

  $scope.init = function () {
    $scope.paging.list();
  };

  $scope.paging = {
    size: 5,
    page: 0,
    totalPages: 0,

    list: function (pageNumber) {
      var pNumber = angular.isUndefined(pageNumber) ? this.page : pageNumber;
      this.page = pNumber;
      $http.get(URL + '?size=' + this.size + '&page=' + pNumber).then(function (value) {
        $scope.tasks = value;
        $scope.paging.totalPages = value.data.totalPages;
      }, alertMessage);
    }
  };

  $scope.taskMaker = {
    message: '',
    create: function () {
      $http.post(URL, this.message).then(function (response) {
        $scope.paging.list();
      }, alertMessage);
    }
  };

  $scope.modifier = {
    targetTask: {},
    completed: function (taskId) {
      $http.put(URL + '/complete/' + taskId).then(function (value) {
        alert('success');
        $scope.paging.list();
      }, alertMessage);
    }
  };

  $scope.modifyModal = {
    targetTask: {},
    title: '',
    message: '',
    refId: '',
    open: function (task) {
      this.targetTask = task;
      this.title = 'modify task :' + task.taskId;
      this.message = task.message;
      this.refId = '';
    },
    updateMessage: function () {
      $http.put(URL + "/message/" + this.targetTask.taskId, this.message).then(function (value) {
        angular.element("#modifyModal").modal("hide");
        alert('success');
        $scope.paging.list();
      }, alertMessage);
    },
    updateRefTask: function () {
      $http.put(URL + "/reference?parentTaskId=" + this.refId + '&childTaskId=' + this.targetTask.taskId).then(function (value) {
        alert('success');
        angular.element("#modifyModal").modal("hide");
        $scope.paging.list();
      }, alertMessage);
    }
  }
});

todoListApp.filter('range', function () {
  return function (input, total) {
    total = parseInt(total);

    for (var i = 0; i < total; i++) {
      input.push(i);
    }

    return input;
  }
});

todoListApp.directive('pagination', function () {
  var pagesInRange = function (currentPageNum, totalPageCount) {
    var RANGE_SIZE = 10;
    var currentRange = Math.floor(currentPageNum / RANGE_SIZE);
    var startPageNum = (currentRange * RANGE_SIZE);
    var endPageNum = Math.min((currentRange + 1) * RANGE_SIZE, totalPageCount) - 1;

    var pages = [];
    for (var i = startPageNum; i <= endPageNum; i++) {
      pages.push(i);
    }
    return pages;
  };

  return {
    restrict: 'E',
    scope: {
      pageInfo: '=pageInfo',
      goPage: '&goPage'
    },
    link: function (scope, element, attrs) {
      scope.pagesInRange = pagesInRange;
    },
    templateUrl: '/directives/pagination.html'
  };
});