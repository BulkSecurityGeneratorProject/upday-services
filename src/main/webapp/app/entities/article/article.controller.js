(function() {
    'use strict';

    angular
        .module('updayApp')
        .controller('ArticleController', ArticleController);

    ArticleController.$inject = ['Article'];

    function ArticleController(Article) {

        var vm = this;

        vm.articles = [];

        loadAll();

        function loadAll() {
            Article.query(function(result) {
                vm.articles = result;
                vm.searchQuery = null;
            });
        }
    }
})();
