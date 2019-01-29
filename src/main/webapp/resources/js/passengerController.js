function PasengerService($resource) {
    return $resource('rest/passenger/:login/new_order', { login: '@login' });
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

function PassCtrl($scope, $http, PassengerService, InfoShareService){
    this.isRegister = false;
    $scope.login = InfoShareService.getUser();
    alert("start gtting info");
    OperatorService.query({login:$scope.login, reqType: "orders"}, function (value){$scope.orders = value;});


    this.newOrder = function(){
        if (isEmpty($scope.srcAddr)){
            alert("Enter source address, please!");
        } else if(isEmpty($scope.dstAddr)){
            alert("Enter destination address, please!");
        } else{
            alert("We add your order!");
            /*InfoShareService.setUser($scope.login);
            window.location.href = '#/' + $scope.userType;*/

        }

    }

    this.register = function() {
        this.isRegister = !this.isRegister;
    }
}


app
    .service('InfoShareService', InfoShareService)
    .factory('PassengerService', PassengerService)
    .controller('PassCtrl', PassCtrl);