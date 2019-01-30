function PassengerService($resource) {
    return $resource('rest/passenger/:login/:TypeReq', { login: '@login',TypeReq: '@TypeReq' });
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

function PassengerCtrl($scope, $http, PassengerService, InfoShareService){
    this.needNew = false;
    $scope.login = InfoShareService.getUser();
    //alert("start gtting info");
    PassengerService.query({login:$scope.login, TypeReq:"orders"}, function (value){$scope.orders = value;});


    this.newOrder = function(){
        if (isEmpty($scope.srcAddr)){
            alert("Enter source address, please!");
        } else if(isEmpty($scope.dstAddr)){
            alert("Enter destination address, please!");
        } else{
            alert("We add your order!");
            PassengerService.query({login:$scope.login, TypeReq:"new_order"}, function (value){$scope.orders = value;});
            /*InfoShareService.setUser($scope.login);
            window.location.href = '#/' + $scope.userType;*/

        }

    };

    this.showNew = function(){
    alert("selected new");
        this.needNew = !this.needNew;
    };
}


app
    .service('InfoShareService', InfoShareService)
    .factory('PassengerService', PassengerService)
    .controller('PassengerCtrl', PassengerCtrl);