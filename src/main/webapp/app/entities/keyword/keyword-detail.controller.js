(function() {
    'use strict';

    angular
        .module('updayApp')
        .controller('KeywordDetailController', KeywordDetailController);

    KeywordDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Keyword'];

    function KeywordDetailController($scope, $rootScope, $stateParams, previousState, entity, Keyword) {
        var vm = this;

        vm.keyword = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('updayApp:keywordUpdate', function(event, result) {
            vm.keyword = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
