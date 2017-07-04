(function() {
    'use strict';

    angular
        .module('updayApp')
        .controller('ArticleDetailController', ArticleDetailController);

    ArticleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Article', 'Author', 'Keyword'];

    function ArticleDetailController($scope, $rootScope, $stateParams, previousState, entity, Article, Author, Keyword) {
        var vm = this;

        vm.article = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('updayApp:articleUpdate', function(event, result) {
            vm.article = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
