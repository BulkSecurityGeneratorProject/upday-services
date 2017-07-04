(function() {
    'use strict';

    angular
        .module('updayApp')
        .controller('KeywordDialogController', KeywordDialogController);

    KeywordDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Keyword'];

    function KeywordDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Keyword) {
        var vm = this;

        vm.keyword = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.keyword.id !== null) {
                Keyword.update(vm.keyword, onSaveSuccess, onSaveError);
            } else {
                Keyword.save(vm.keyword, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('updayApp:keywordUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
