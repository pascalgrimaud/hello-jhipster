'use strict';

angular.module('hellojhipsterApp')
    .factory('LabelSearch', function ($resource) {
        return $resource('api/_search/labels/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
