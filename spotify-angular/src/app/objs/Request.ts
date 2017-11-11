export class Request{
    data : any;
    created_by : string = "skamal";
    requested_date : Date = new Date("February 4, 2016 10:13:00");
    status : string = "Pending";
    approvedBy: string;

    constructor(data){
        this.data = data;
    }
}