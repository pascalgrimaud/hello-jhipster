'use strict';

angular.module('hellojhipsterApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


