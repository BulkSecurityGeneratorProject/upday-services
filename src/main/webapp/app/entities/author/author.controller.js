(function() {
    'use strict';

    angular
        .module('updayApp')
        .controller('AuthorController', AuthorController);

    AuthorController.$inject = ['Author'];

    function AuthorController(Author) {

        var vm = this;

        vm.authors = [];

        loadAll();

        function loadAll() {
            Author.query(function(result) {
                vm.authors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
