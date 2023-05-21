import mongoose from 'mongoose';
import jwt from 'jsonwebtoken';

const User = mongoose.model("User");
const Post = mongoose.model("Post");
const FriendRequest = mongoose.model("FriendRequest")

const signUp = (req, res) => {
    const newUser = new User({
        firstName: req.body.firstName,
        lastName: req.body.lastName,
        email: req.body.email,
        password: req.body.password,
        friends: []
    });

    console.log(newUser)

    newUser.save((err)=>{
        if(!err) {return res.send({success:true});}
        else{return res.send({success:false});}
    });
}

const logIn = (req, res) => {
    const email = req.body.email.trim();
    const password = req.body.password;

    User.findOne({email}, (err, user)=>{
        if(err || !user){
            console.log("User does not exist!");
            return res.send({success: false});
        }
    
        user.comparePassword(password, (err, isMatch)=>{
            if (err || !isMatch){
                console.log("Not the correct password");
                return res.send({success: false});
            }
            console.log("Password matched!");


            const tokenPayload = {
                _id: user._id
            }

            const token = jwt.sign(tokenPayload, "i-am-not-a-secret-string")


            return res.send({success: true, token, firstName:user.firstName, lastName:user.lastName, friends:user.friends, userId: user._id})
        })
    })
}

const checkIfLoggedIn = (req, res) => {
    if(!req.cookies || !req.cookies.authtoken){
        return res.send({isLoggedIn: false});
    }

    return jwt.verify(
        req.cookies.authtoken,
        "i-am-not-a-secret-string",
        (err, tokenPayload) => {
            if(err){
                return res.send({isLoggedIn:false})
            }

            const userId = tokenPayload._id

            return User.findById(userId, (userErr, user) => {
                if(userErr || !user){
                    return res.send({isLoggedIn:false})
                }

                return res.send({isLoggedIn:true})
            })
        }
    )
}

const createPost = (req, res) => {
    const newPost = new Post({
        poster: req.body.posterid,
        date: new Date(),
        content: req.body.content,
        authorFirstName: req.body.firstname,
        authorLastName: req.body.lastname
    });

    console.log(newPost)

    newPost.save((err)=>{
        if(!(err)){return res.send({success:true});}
        else {return res.send({success:false});}
    })
}

const returnObjectId = (str) => {
    return mongoose.Types.ObjectId(str);
}

const getPosts = (req, res) => {
    const idlist = req.body.idlist;

    let objectlist = idlist.map(returnObjectId);

    Post.find({poster:{$in: objectlist}}).sort({date: -1}).exec(
        (err, posts)=>{
            if(!err){
                if(posts==null){return res.send({success: false, postlist: []})}
                else{return res.send({success: true, postlist: posts});}
            } else {return res.send({success: false, postlist: []});}
    })
}

const deletePost = (req, res) => {
    const todel = returnObjectId(req.body.postid);

    Post.deleteOne({_id: todel}, 
        (err)=>{
            if(!err){return res.send({success: true})}
            else{return res.send({success: false})}
        })
}

const editPost = (req, res) => {
    const toedit = returnObjectId(req.body.postid);
    const newContent = req.body.content;

    Post.findOneAndUpdate({_id:toedit}, {$set:{content:newContent}},
        (err) =>{
            if(!err){return res.send({success: true})}
            else{return res.send({success: false})}
        }
    )
}

const searchPeople = (req, res) => {
    const query = req.body.query

    User.find({$or: [{firstName: {$in: query}},{lastName: {$in: query}}]}, (err, users)=>{
        if(!err){return res.send({success: true, people: users})}
        else{return res.send({success:false})}
    })
}

const sendFriendRequest = (req, res) => {

    const newFriendRequest = new FriendRequest({
        sender: returnObjectId(req.body.sender),
        receiver: returnObjectId(req.body.receiver),
        senderFirstName: req.body.firstname,
        senderLastName: req.body.lastname
    });

    FriendRequest.findOne({sender: newFriendRequest.sender, receiver: newFriendRequest.receiver}, (err, fr)=>{
        if(fr){return res.send({success:false, existing: true});}
        else{
            newFriendRequest.save((err)=>{
                if(!err){return res.send({success:true});}
                else{return res.send({success:false, existing:false});}
            });
        }
    })
}

const getFriendRequests = (req, res) => {
    const userid = returnObjectId(req.body.userid);

    FriendRequest.find({receiver: userid}, (err, friendrequests) =>{
        if(!err){return res.send({success:true, requests: friendrequests})}
        else{return res.send({success:false})}
    })
}

const answerFriendRequest = (req, res) => {
    const frid = returnObjectId(req.body.frid);
    const answer = req.body.answer;
    const receiver = returnObjectId(req.body.receiver);
    const sender = returnObjectId(req.body.sender);

    if(answer){
        User.findOneAndUpdate({_id:sender}, {$push: {friends: receiver}}, (err1, result1)=>{
            if(!err1){
                User.findOneAndUpdate({_id:receiver}, {$push: {friends: sender}}, (err2, result2)=>{
                    if(!err2){
                        FriendRequest.deleteOne({_id:frid}, (err)=>{
                            if(!err){
                                // 
                                User.findOne({_id:receiver}, (err, user)=>{
                                    if(!err){
                                        return res.send({success: true, newFriends: user.friends});
                                    } else{return res.send({success:false});}
                                })
                            }
                            else{return res.send({success:false});}
                        })
                    }
                    else{return res.send({success:false});}
                })
            } else {return res.send({success: false});}
        })

    }
    else{
        FriendRequest.deleteOne({_id:frid},(err)=>{
            if(!err){return res.send({success: true});}
            else{return res.send({success:false});}
        })
    }
}

const getFriends = (req, res) => {
    const friends = req.body.friends

    let friendObjectIds = friends.map(returnObjectId);

    User.find({_id:{$in: friendObjectIds}},
        (err, friends)=>{
            if(!err){
                if(friends==null){return res.send({success: false, friendslist: []})}
                else{return res.send({success: true, friendslist: friends});}
            } else {return res.send({success: false, friendslist: []});}
    })
}


export {signUp, logIn, checkIfLoggedIn, createPost, getPosts, deletePost, editPost, 
    searchPeople, sendFriendRequest, getFriendRequests, answerFriendRequest, getFriends}