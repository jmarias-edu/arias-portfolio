import React from 'react'
import './Main.css'

class Posts extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            newPost: "",
            posts:[]
        }

        this.newPostChangeHandler = this.newPostChangeHandler.bind(this);
        this.makeNewPost = this.makeNewPost.bind(this);
        this.editPost = this.editPost.bind(this);
        this.deletePost = this.deletePost.bind(this);
    }

    newPostChangeHandler(e){
        this.setState({ newPost: e.target.value });
    }

    makeNewPost(e){
        e.preventDefault()
        

        const newPost ={
            posterid: localStorage.getItem("userid"),
            content: this.state.newPost,
            firstname: localStorage.getItem("firstname"),
            lastname: localStorage.getItem("lastname")
        }

        fetch(
            "http://localhost:3001/create-post",
            {
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body: JSON.stringify(newPost)
            })
            .then(response=>response.json())
            .then(body=>{
                if (body.success) {alert("Posted Successfully!"); window.location.reload();}
                else { alert("Post not Successful"); }
            })
    }

    componentDidMount(){

        let ids = []
        if(localStorage.getItem("friends")) {ids =localStorage.getItem("friends").split(",");}
        ids.push(localStorage.getItem("userid"));

        const payload = {
            idlist: ids
        }

        fetch(
            "http://localhost:3001/get-posts",
            {
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body: JSON.stringify(payload)
            })
            .then(response=>response.json())
            .then(body=>{
                if (body.success) {
                    this.setState({posts: body.postlist});
                    console.log("Posts received successfully");
                }
                else {console.log("Posts not received")}
            })
    }

    editPost(objectid){
        const newcontent = prompt("Input new content of post! Leave empty to cancel");
        
        if(!(newcontent===null && newcontent==="")){
            const payload = {
                postid: objectid,
                content: newcontent
            }
            fetch(
                "http://localhost:3001/edit-post",
                {
                    method:"POST",
                    headers:{"Content-Type":"application/json"},
                    body: JSON.stringify(payload)
                })
                .then(response=>response.json())
                .then(body=>{
                    if(body.success){alert("Post successfully edited!"); window.location.reload();}
                    else{alert("Post not edited")}
                })
        }
        
    }

    deletePost(objectid){
        const newcontent = prompt("Type 'delete' to delete your post");
        
        if(newcontent==="delete"){
            const payload = {
                postid: objectid,
            }
            fetch(
                "http://localhost:3001/delete-post",
                {
                    method:"POST",
                    headers:{"Content-Type":"application/json"},
                    body: JSON.stringify(payload)
                })
                .then(response=>response.json())
                .then(body=>{
                    if(body.success){alert("Post successfully deleted!"); window.location.reload();}
                    else{alert("Post not deleted")}
                })
        }
    }

    render(){
        return(
            <div className='posts'>
                <form className="post" id="newpost" onSubmit={this.makeNewPost}>
                    <p className="name">What's Up?</p>
                    <input type="textarea" id="newpost" value={this.state.newPost} onChange={this.newPostChangeHandler}/>
                    <button type="submit" id="newpost">Post</button>
                </form>
                {
                    this.state.posts.map((posts)=>{
                        return (
                            <div className="post" key={posts._id}>
                                <p className="name">{posts.authorFirstName} {posts.authorLastName}</p> 
                                <p className="time">Posted at {posts.date}</p>
                                <p className='content'>{posts.content}</p><hr />
                                <p>Like | Comment | Share | &nbsp;
                                {posts.poster === localStorage.getItem("userid") ? <button onClick={()=>this.editPost(posts._id)}>Edit</button> : ""} &nbsp;
                                {posts.poster === localStorage.getItem("userid") ? <button onClick={()=>this.deletePost(posts._id)}>Delete</button>: ""}
                                </p> 
                            </div>
                            )
                    })
                }
               
            </div>
        )
    }
}

export default Posts