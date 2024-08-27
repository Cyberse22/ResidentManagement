
function confirmDelete(url) {
    const confirmed = window.confirm("Bạn có chắc chắn muốn khóa cư dân không?");
    if (confirmed) {
        deleteResident(url);
    }
}

//chuyen trang thai resident 
function deleteResident(url) {
    fetch(url, {
        method: 'delete'
    }).then(res => {
        if (res.status === 204)
        {
            alert("Khóa cư dân thành công");
            location.reload();
        }
        else
            alert("ERROR");
    });
};



//chuyen trang thai survey
function blockSurvey(url) {
    
    fetch(url, {
        method: 'delete'
    }).then(res => {
        if (res.status === 204)
            location.reload();
        else
            alert("ERROR");
    });
};

//chuyen trang thai visitor
function deleteVisitor(url) {
    
    fetch(url, {
        method: 'delete'
    }).then(res => {
        if (res.status === 204)
        {
            alert("Xóa thành công !")
            location.reload();
        }
        else
            alert("ERROR");
    });
};


//xoa item
function deleteItem(url) {
    
    fetch(url, {
        method: 'delete'
    }).then(res => {
        if (res.status === 204)
        {
            alert("Xóa thành công !")
            location.reload();
        }
        else
            alert("ERROR");
    });
};

//chuyen trang thai feedback
function solveFeedback(url) {
    
    fetch(url, {
        method: 'delete'
    }).then(res => {
        if (res.status === 204)
        {
            alert("Xử lý thành công");
            location.reload();
        }
        else
        {
            console.log(url);
            alert("ERROR");
        }
           
    });
};


//xoa feedback
function confirmDeleteFeedback(url) {
    const confirmed = window.confirm("Bạn có chắc muốn xóa phản hồi này không ?");
    if (confirmed) {
        deleteFeedback(url);
    }
};
function deleteFeedback(url) {
    fetch(url, {
        method: 'delete'
    }).then(res => {
        if (res.status === 204)
        {
            alert("Xóa thành công !");
            location.reload();
        }
        else
            alert("ERROR");
    });
};


//xoa survey
function confirmDeleteSurvey(url) {
    const confirmed = window.confirm("Bạn có chắc muốn xóa khảo sát này không ?");
    if (confirmed) {
        deleteSurvey(url);
    }
};
function deleteSurvey(url) {
    fetch(url, {
        method: 'delete'
    }).then(res => {
        if (res.status === 204)
        {
            alert("Xóa thành công !");
            location.reload();
        }
        else
            alert("ERROR");
    });
};

//xoa resident
function confirmDeleteResident(url) {
    const confirmed = window.confirm("Bạn có chắc muốn xóa cư dân này không ?");
    if (confirmed) {
        deleteResidentPermanently(url);
    }
};
function deleteResidentPermanently(url) {
    fetch(url, {
        method: 'delete'
    }).then(res => {
        if (res.status === 204)
        {
            alert("Xóa thành công !");
            location.reload();
        }
        else
            alert("ERROR");
    });
};