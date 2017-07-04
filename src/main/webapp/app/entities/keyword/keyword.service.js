(function() {
    'use strict';
    angular
        .module('updayApp')
        .factory('Keyword', Keyword);

    Keyword.$inject = ['$resource'];

    function Keyword ($resource) {
        var resourceUrl =  'api/keywords/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
