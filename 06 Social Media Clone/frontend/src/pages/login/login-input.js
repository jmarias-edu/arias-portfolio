import React from 'react'
import Cookies from "universal-cookie"
import {Navigate} from 'react-router-dom';
import './login.css'

class LogInInput extends React.Component{
    constructor(props){
        super(props);

        this.logIn = this.logIn.bind(this);
    }

    logIn(event) {
        event.preventDefault();

        const credentials = {
            email: document.getElementById("le").value,
            password: document.getElementById("lp").value
        }

        fetch(
            "http://localhost:3001/login",
            {
                method: "POST",
                headers: {
                    "Content-type":"application/json"
                },
                body: JSON.stringify(credentials)
            })
            .then(response => response.json())
            .then(body => {
                if(!body.success) {alert("Failed to Log in")}
                else{
                    const cookies = new Cookies();
                    cookies.set(
                        "authtoken",
                        body.token,
                        {
                            path: "localhost/3001/",
                            age: 60*60,
                            sameSite: "lax"
                        });
                        localStorage.setItem("firstname", body.firstName);
                        localStorage.setItem("lastname", body.lastName);
                        localStorage.setItem("friends", body.friends);
                        localStorage.setItem("userid", body.userId);
                        console.log("Successfully logged in!");
                        window.location.reload();

                }
            })

    }

    render() {
        if(localStorage.getItem("userid")){
            return(<Navigate to="/feed" />);
        }
        else{
            return(
                <div className="loginmain">
                    <form onSubmit={this.logIn} id='myForm'>
                        <p className='loginsub'>Login to your <br/>Calamansee <br/> account!</p>
                        <label htmlFor="le">Email</label><br/>
                        <input type='email' id="le" required /><br/>
        
                        <label htmlFor="lp">Password</label><br/>
                        <input type='password' id="lp" required /> <br/>
        
                        <button type="submit" className="login1">Log In</button>
                    </form>
                </div>
            )
        }
    }
}

export default LogInInput