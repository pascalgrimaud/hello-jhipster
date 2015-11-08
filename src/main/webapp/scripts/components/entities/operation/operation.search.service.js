'use strict';

angular.module('helloJhipsterApp')
    .factory('OperationSearch', function ($resource) {
        return $resource('api/_search/operations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
