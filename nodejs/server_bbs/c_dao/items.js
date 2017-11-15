exports.Bbs = function(){
    this.no = -1;
    this.title = "";
    this.content = "";
    this.date = "";
    this.user_id = "";

    this.toQuery = function(){
        return {
            no : this.no,
            title : this.title,
            content : this.content,
            date : this.date,
            user_id : this.user_id
        };
    };
};

exports.Search = function(){
    this.type = "";
    this.no = -1;
    this.title = "";
    this.content = "";
    this.date = "";
    this.user_id = "";

    this.toQuery = function(){
        return {
            type : this.type,
            no : this.no,
            title : this.title,
            content : this.content,
            date : this.date,
            user_id : this.user_id
        };
    };
};