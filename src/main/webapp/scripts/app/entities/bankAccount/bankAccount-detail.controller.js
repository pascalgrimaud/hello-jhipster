'use strict';

angular.module('hellojhipsterApp')
    .controller('BankAccountDetailController', function ($scope, $rootScope, $stateParams, entity, BankAccount, User, Operation) {
        $scope.bankAccount = entity;
        $scope.load = function (id) {
            BankAccount.get({id: id}, function(result) {
                $scope.bankAccount = result;
            });
        };
        var unsubscribe = $rootScope.$on('hellojhipsterApp:bankAccountUpdate', function(event, result) {
            $scope.bankAccount = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
