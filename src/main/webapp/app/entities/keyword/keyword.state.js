(function() {
    'use strict';

    angular
        .module('updayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('keyword', {
            parent: 'entity',
            url: '/keyword',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'updayApp.keyword.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/keyword/keywords.html',
                    controller: 'KeywordController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('keyword');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('keyword-detail', {
            parent: 'keyword',
            url: '/keyword/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'updayApp.keyword.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/keyword/keyword-detail.html',
                    controller: 'KeywordDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('keyword');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Keyword', function($stateParams, Keyword) {
                    return Keyword.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'keyword',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('keyword-detail.edit', {
            parent: 'keyword-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword/keyword-dialog.html',
                    controller: 'KeywordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Keyword', function(Keyword) {
                            return Keyword.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('keyword.new', {
            parent: 'keyword',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword/keyword-dialog.html',
                    controller: 'KeywordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('keyword', null, { reload: 'keyword' });
                }, function() {
                    $state.go('keyword');
                });
            }]
        })
        .state('keyword.edit', {
            parent: 'keyword',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword/keyword-dialog.html',
                    controller: 'KeywordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Keyword', function(Keyword) {
                            return Keyword.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('keyword', null, { reload: 'keyword' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('keyword.delete', {
            parent: 'keyword',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword/keyword-delete-dialog.html',
                    controller: 'KeywordDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Keyword', function(Keyword) {
                            return Keyword.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('keyword', null, { reload: 'keyword' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
