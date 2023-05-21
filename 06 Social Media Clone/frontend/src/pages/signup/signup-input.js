// Signup template from Exercise 9
import React from 'react'
import {validatePassword, validateRepeatPassword} from './validator.js'
import './signup.css'

class SignUpInput extends React.Component{
    constructor(props){
        super(props)

        this.state = {
            firstName: "",
            lastName: "",
            email: "",
            pass: "",
            repeatPass:"",
            passPass: true,
            repeatPassPass: true
        }

        this.fnChangeHandler = this.fnChangeHandler.bind(this)
        this.lnChangeHandler = this.lnChangeHandler.bind(this)
        this.eChangeHandler = this.eChangeHandler.bind(this)
        this.pChangeHandler = this.pChangeHandler.bind(this)
        this.rpChangeHandler = this.rpChangeHandler.bind(this)
        this.submitForm = this.submitForm.bind(this)
    }

    fnChangeHandler(e){
        this.setState({ firstName: e.target.value });
    }

    lnChangeHandler(e){
        this.setState({ lastName: e.target.value });
    }

    eChangeHandler(e){
        this.setState({ email: e.target.value });
    }

    pChangeHandler(e){
        this.setState({ pass: e.target.value });
        this.setState({ passPass: validatePassword(e.target.value) })

        if(e.target.value.length>0){
            document.getElementById("rp").disabled = false;
            
        } 
        else {
            this.setState({ repeatPass: "" });
            document.getElementById("rp").disabled = true;
            document.getElementById("btn").disabled = false;
        }

        if(this.state.repeatPass.length>0){
            const result = validateRepeatPassword(e.target.value, this.state.repeatPass)
            this.setState({ repeatPassPass: result })

            // if(!result){
            //     document.getElementById("btn").disabled = true;
            // } else { document.getElementById("btn").disabled = false; }
        }
    }

    rpChangeHandler(e){
        const result = validateRepeatPassword(e.target.value, this.state.pass)
        this.setState({ repeatPass: e.target.value })
        this.setState({ repeatPassPass: result })

        // if(!result){
        //     document.getElementById("btn").disabled = true;
        // } else { document.getElementById("btn").disabled = false; }
    }

    submitForm(event){
        event.preventDefault();
        if(this.state.pass === this.state.repeatPass && this.state.passPass){

            const user= {
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                email: this.state.email,
                password: this.state.pass
            }

            console.log(user);

            fetch(
                "http://localhost:3001/signup",
                {
                    method: "POST",
                    headers:{"Content-Type": "application/json"},
                    body: JSON.stringify(user)
                })
                .then(response => response.json())
                .then(body => {
                    if (body.success) {alert("Successfully saved user"); window.location.replace("http://localhost:3000/log-in");}
                    else { alert("Failed to save user"); }
                });

        } else {
            if(!(this.state.pass === this.state.repeatPass)){alert("Passwords do not match please try again")}
            else if(!this.state.passPass){alert("Password not valid please try again")}
        }
    }

    render() {
        return(
            <div className="window">
                <form onSubmit={this.submitForm} id='myForm'>
                    <p className="signup">Sign up here! <br/>See you at <br/>Calamansee!</p>
                    <label htmlFor="fn">First Name</label><br/>
                    <input type='text' id="fn" value = {this.state.firstName} onChange={this.fnChangeHandler} required /><br/>

                    <label htmlFor="ln">Last Name</label><br/>
                    <input type='text' id="ln" value = {this.state.lastName} onChange={this.lnChangeHandler} required /><br/>

                    <label htmlFor="e">Email</label><br/>
                    <input type='email' id="e" value = {this.state.email} onChange={this.eChangeHandler} required /><br/>

                    <label htmlFor="p">Password</label><br/>
                    <input type='password' id="p" value = {this.state.pass} onChange={this.pChangeHandler} required /> &nbsp; {this.state.passPass ? "" : <div><br/> Password must Have at <br/>least 8 characters, <br/>1 Uppercase letter,<br/> 1 Lowercase letter<br/> and 1 number</div>}<br/>

                    <label htmlFor="rp">Repeat Password</label><br/>
                    <input type='password' id="rp" value = {this.state.repeatPass} onChange={this.rpChangeHandler} required disabled /> &nbsp; { (this.state.passPass && this.state.repeatPassPass) || (this.state.repeatPass.length === 0) ? "" : <div><br/> Passwords do not Match</div>}<br/>

                    <button type="submit" className="signup1">Sign up</button>
                </form>
            </div>
        )
    }
}

export default SignUpInput