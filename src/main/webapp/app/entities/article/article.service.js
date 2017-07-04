(function() {
    'use strict';
    angular
        .module('updayApp')
        .factory('Article', Article);

    Article.$inject = ['$resource', 'DateUtils'];

    function Article ($resource, DateUtils) {
        var resourceUrl =  'api/articles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.publicationDate = DateUtils.convertDateTimeFromServer(data.publicationDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
