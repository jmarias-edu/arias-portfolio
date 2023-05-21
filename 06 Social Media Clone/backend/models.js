import mongoose from 'mongoose';
import bcrypt from 'bcrypt';


const userSchema = new mongoose.Schema({
    firstName: {type: String, required: true},
    lastName: {type: String, required: true},
    email: {type: String, required: true},
    password: {type: String, required: true},
    friends: [mongoose.Schema.Types.ObjectId]
});

userSchema.pre("save", function(next){
    const user = this;

    if(!user.isModified("password")) return next();

    return bcrypt.genSalt((saltError, salt) => {
        if(saltError) {return next(saltError);}

        return bcrypt.hash(user.password, salt, (hashError, hash) => {
            if(hashError) {return next(hashError);}

            user.password = hash
            return next();
        })
    })
})

userSchema.methods.comparePassword = function(password, callback){
    bcrypt.compare(password, this.password, callback);
}

const postSchema = new mongoose.Schema({
    poster: {type:mongoose.Schema.Types.ObjectId, required: true},
    date: {type: Date, required: true},
    content: {type: String, required: true},
    authorFirstName: {type: String, required: true},
    authorLastName: {type: String, required: true}
})

const friendRequest = new mongoose.Schema({
    sender: {type:mongoose.Schema.Types.ObjectId, required: true},
    receiver: {type:mongoose.Schema.Types.ObjectId, required: true},
    senderFirstName: {type: String, required: true},
    senderLastName: {type: String, required: true}
})

mongoose.model("User", userSchema);
mongoose.model("Post", postSchema);
mongoose.model("FriendRequest", friendRequest)