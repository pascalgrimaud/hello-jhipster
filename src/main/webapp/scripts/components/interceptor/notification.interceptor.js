 'use strict';

angular.module('helloJhipsterApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-helloJhipsterApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-helloJhipsterApp-params')});
                }
                return response;
            }
        };
    });
