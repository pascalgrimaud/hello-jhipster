'use strict';

angular.module('helloJhipsterApp')
    .factory('BankAccountSearch', function ($resource) {
        return $resource('api/_search/bankAccounts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
