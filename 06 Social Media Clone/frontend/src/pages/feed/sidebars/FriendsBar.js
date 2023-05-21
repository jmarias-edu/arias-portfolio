import React from 'react'
import './Sidebar.css'
import './FriendsBar.css'

class FriendsList extends React.Component{
    constructor(props){
        super(props)

        this.state ={
            friends: [],
            searchPeople: "",
            peopleSearched: [],
            friendRequests:[]
        }

        this.searchFriendsHandler = this.searchFriendsHandler.bind(this);
        this.searchPeople = this.searchPeople.bind(this);
        this.addFriendRequest = this.addFriendRequest.bind(this);
    }

    searchFriendsHandler(e){
        this.setState({searchPeople: e.target.value})
    }

    searchPeople(event){
        event.preventDefault();

        let newQuery = {query: this.state.searchPeople.split(" ")};

        fetch(
            "http://localhost:3001/search-people",
            {
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body: JSON.stringify(newQuery)
            })
            .then(response=>response.json())
            .then(body=>{
                if (body.success) {console.log("Search successful!"); console.log(body.people);this.setState({peopleSearched: body.people});}
                else { console.log("Search not successful"); }
            })
    }

    addFriendRequest(requestid){
        const friends = localStorage.getItem("friends");

        const payload = {
            sender: localStorage.getItem("userid"),
            receiver: requestid,
            firstname: localStorage.getItem("firstname"),
            lastname: localStorage.getItem("lastname")
        }

        const senderids = this.state.friendRequests.map(request=>request.sender);

        if (friends.includes(payload.receiver)){alert("Already your friend!"); return}
        //Add check for if in friend request
        if (senderids.includes(payload.receiver)){alert("You have a pending friend request with this person!"); return}

        fetch(
            "http://localhost:3001/send-friend-request",
            {
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body: JSON.stringify(payload)
            })
            .then(response=>response.json())
            .then(body=>{
                if (body.success) {
                    alert("Sent request successfully!")
                }
                else {
                    if(body.existing){alert("You have an outgoing friend request with this person!")}
                    else{alert("Request not successful");}
                }
            })
    }

    answerFriendRequest(requestid, decision, senderid, receiverid){
        const payload = {
            frid: requestid,
            answer: decision,
            sender: senderid,
            receiver: receiverid
        }

        fetch(
            "http://localhost:3001/answer-friend-request",
            {
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body: JSON.stringify(payload)
            })
            .then(response=>response.json())
            .then(body=>{
                if (body.success) {
                    alert("Request successful!");
                    if(decision){
                        localStorage.setItem("friends", body.newFriends);
                    }
                    window.location.reload();
                }
                else {alert("Request not successful");}
            })
    }

    componentDidMount(){
        const userid = {userid: localStorage.getItem("userid")}

        fetch(
            "http://localhost:3001/get-friend-requests",
            {
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body: JSON.stringify(userid)
            })
            .then(response=>response.json())
            .then(body=>{
                if (body.success) {
                    console.log("Friend Requests Received successfully!"); 
                    this.setState({friendRequests:body.requests});
                    console.log(this.state.friendRequests);
                }
                else {alert("Friend Requests Received unsuccessfully");}
            })
        
        let ids = []
        if(localStorage.getItem("friends")) {ids =localStorage.getItem("friends").split(",");}

        const payload = {
            friends: ids
        }

        fetch(
            "http://localhost:3001/get-friends",
            {
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body: JSON.stringify(payload)
            })
            .then(response=>response.json())
            .then(body=>{
                if (body.success) {
                    console.log("Friends Received successfully!"); 
                    this.setState({friends:body.friendslist});
                }
                else {console.log("Friend Requests Received unsuccessfully");}
            })

    }

    render(){
        return(
            <div className="sidebar">
                <div>
                    <form onSubmit={this.searchPeople}>
                        <h1>Search People</h1>
                        <input type="text" value ={this.state.searchPeople} onChange={this.searchFriendsHandler} required/>&nbsp;
                        <button type="submit">Search</button>
                        <hr/>
                    </form>
                    {
                        this.state.peopleSearched.map((people)=>{
                            if(people._id===localStorage.getItem("userid")) {return(<div key={people._id}/ >)}

                            return (
                                <div className='profile' key={people._id}>
                                    <p className='profile'>
                                        <div><img src='/profile.png' className='friendpic left' alt='friend'/>
                                        <b>{people.firstName} {people.lastName}'s profile</b></div>
                                        Firstname: {people.firstName}<br/>
                                        Lastname: {people.lastName}<br/>
                                        Email: {people.email}<br/>
                                        <button onClick={()=>this.addFriendRequest(people._id)} className="big">Add Friend</button>
                                    </p>
                                    
                                </div>
                                )
                        })
                    }
                </div>
                <div>
                    <h1>Friend Requests</h1>
                    <hr/>
                    {
                        this.state.friendRequests.map((request)=>{
                            return (
                                <div className='profile' key={request._id}>
                                    <p className='profile'>
                                        <div><img src='/profile.png' className='friendpic left' alt='friend'/>
                                        <b>{request.senderFirstName} {request.senderLastName}</b></div><br/>
                
                                        <button className="fr" onClick={()=>this.answerFriendRequest(request._id, true, request.sender, request.receiver)}>Accept</button>
                                        <button className="fr" onClick={()=>this.answerFriendRequest(request._id, false, request.sender, request.receiver)}>Delete</button>
                                    </p>
                                </div>
                                )
                        })
                    }
                </div>
                <div>
                <h1>Friends</h1>
                    <hr />
                    {
                        this.state.friends.map((friends)=>{
                            return (
                                <div className='friend' key={friends._id}>
                                    <img src='/profile.png' className='friendpic' alt='friend'/>
                                    <p>{friends.firstName} {friends.lastName}</p>
                                </div>
                                )
                        })
                    }
                </div>
            </div>
        )
    }
}

export default FriendsList