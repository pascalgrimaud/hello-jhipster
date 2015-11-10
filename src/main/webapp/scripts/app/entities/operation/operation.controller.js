'use strict';

angular.module('helloJhipsterApp')
    .controller('OperationController', function ($scope, $state, $modal, Operation, OperationSearch, ParseLinks) {
      
        $scope.operations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Operation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.operations.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.operations = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            OperationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.operations = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.operation = {
                date: null,
                description: null,
                amount: null,
                id: null
            };
        };
    });
