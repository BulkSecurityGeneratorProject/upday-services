(function() {
    'use strict';

    angular
        .module('updayApp')
        .controller('KeywordController', KeywordController);

    KeywordController.$inject = ['Keyword'];

    function KeywordController(Keyword) {

        var vm = this;

        vm.keywords = [];

        loadAll();

        function loadAll() {
            Keyword.query(function(result) {
                vm.keywords = result;
                vm.searchQuery = null;
            });
        }
    }
})();
