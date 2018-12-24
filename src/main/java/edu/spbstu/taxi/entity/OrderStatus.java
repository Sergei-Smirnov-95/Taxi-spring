package edu.spbstu.taxi.entity;

public enum OrderStatus {
    /* Just created by passenger */
    /* Can go to: PROCESSING, DEAD */
    NEW ("NEW"),

    /* Processing by operator: choosing a driver */
    /* Can go to: APPOINTED, DEAD */
    PROCESSING ("PROCESSING"),

    /* Appointed to driver */
    /* Can go to: PROCESSING, DECLINED */
    APPOINTED ("APPOINTED"),

    /*Declined by driver*/
    /* Can go to: PROCESSING */
    DECLINED ("DECLINED"),

    /* Accepted by driver */
    /* Can go to: EXECUTED */
    ACCEPTED ("ACCEPTED"),

    /* Order is executed */
    /* Can go to: <NULL> */
    EXECUTED ("EXECUTED"),

    /* No available drivers or killed by passenger */
    /* Can go to: <NULL> */
    DEAD ("DEAD");

    private final String id;

    OrderStatus(final String id){
        this.id = id != null ? id : "NEW";
    }

    public String getId() {
        return id;
    }

    static boolean isAvailable(final OrderStatus previousState, final OrderStatus nextState){
        boolean result = false;
        switch (previousState){
            case NEW:
                result = nextState == PROCESSING || nextState == DEAD;
                break;
            case PROCESSING:
                result = nextState == APPOINTED || nextState == DEAD;
                break;
            case APPOINTED:
                result = nextState == ACCEPTED || nextState == DECLINED;
                break;
            case DECLINED:
                result = nextState == PROCESSING;
                break;
            case ACCEPTED:
                result = nextState == EXECUTED;
                break;
        }
        return result;
    }
}
