 'use strict';

angular.module('hellojhipsterApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-hellojhipsterApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-hellojhipsterApp-params')});
                }
                return response;
            }
        };
    });
