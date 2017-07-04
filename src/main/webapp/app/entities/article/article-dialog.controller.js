(function() {
    'use strict';

    angular
        .module('updayApp')
        .controller('ArticleDialogController', ArticleDialogController);

    ArticleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Article', 'Author', 'Keyword'];

    function ArticleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Article, Author, Keyword) {
        var vm = this;

        vm.article = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.authors = Author.query();
        vm.keywords = Keyword.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.article.id !== null) {
                Article.update(vm.article, onSaveSuccess, onSaveError);
            } else {
                Article.save(vm.article, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('updayApp:articleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.publicationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
