<!DOCTYPE html>

<html lang="kr" ng-app="app">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="stylesheet" href="/libs/bootstrap/css/bootstrap.min.css" />
  <script src="/libs/jquery-3.3.1.min.js"></script>
  <script src="/libs/popper.min.js"></script>
  <script src="/libs/bootstrap/js/bootstrap.min.js"></script>

  <link href="/libs/bootstrap-toggle/css/bootstrap-toggle.min.css" rel="stylesheet">
  <script src="/libs/bootstrap-toggle/js/bootstrap2-toggle.min.js"></script>

  <script src="/libs/angular.min.js"></script>

  <link rel="stylesheet" href="/css/app.css" />
  <script src="/js/app.js"></script>
</head>
<body ng-controller="appController" ng-init="init()">
<div class="container">
  <div class="col-12">
    <form class="input-group" ng-submit="taskMaker.create()">
      <input ng-model="taskMaker.message" type="text" class="form-control" placeholder="schedule message">
      <div class="input-group-append">
        <button ng-click="taskMaker.create()" class="btn btn-outline-secondary" type="button">create</button>
      </div>
    </form>
    <br>
    <div class="col-12">
      <table class="table">
        <thead>
        <tr>
          <th scope="col">id</th>
          <th scope="col">할일</th>
          <th scope="col">작성일시</th>
          <th scope="col">최종수정일시</th>
          <th scope="col">완료처리</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="task in tasks.data.content" data-toggle="modal" data-target="#modifyModal" ng-click="modifyModal.open(task)">
          <th scope="row">{{task.taskId}}</th>
          <td>{{task.message}} {{task.parentTaskNumbers}}</td>
          <td>{{task.createdAt | date:'yyyy-MM-dd HH:mm:ss'}}</td>
          <td>{{task.updatedAt | date:'yyyy-MM-dd HH:mm:ss'}}</td>
          <td>
            <button class="btn" ng-class="{'btn-secondary': !task.completed, 'btn-primary': task.completed}" ng-click="modifier.completed(task.taskId); $event.stopPropagation();">완료</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <pagination page-info="tasks.data" go-page="paging.list(page)"></pagination>

    <div class="modal fade" id="modifyModal" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{modifyModal.title}}</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="input-group">
              <div class="input-group-prepend">
                <span class="input-group-text">message</span>
              </div>
              <input class="form-control" ng-model="modifyModal.message" type="text" />
              <div class="input-group-prepend">
                <button class="btn btn-outline-secondary" type="button" ng-click="modifyModal.updateMessage()">Modify</button>
              </div>
            </div>

            <br>

            <div class="input-group">
              <div class="input-group-prepend">
                <span class="input-group-text">ref Id</span>
              </div>
              <input type="number" min="0" class="form-control" ng-model="modifyModal.refId" />
              <div class="input-group-prepend">
                <button class="btn btn-outline-secondary" type="button" ng-click="modifyModal.updateRefTask()">Modify</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>