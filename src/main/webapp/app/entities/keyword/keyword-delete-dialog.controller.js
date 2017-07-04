(function() {
    'use strict';

    angular
        .module('updayApp')
        .controller('KeywordDeleteController',KeywordDeleteController);

    KeywordDeleteController.$inject = ['$uibModalInstance', 'entity', 'Keyword'];

    function KeywordDeleteController($uibModalInstance, entity, Keyword) {
        var vm = this;

        vm.keyword = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Keyword.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
