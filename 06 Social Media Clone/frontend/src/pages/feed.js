import React from 'react';
import Cookies from 'universal-cookie';
import FeedBody from './feed/feed-body';
import {Navigate} from 'react-router-dom';

class Feed extends React.Component{
    constructor(props){
        super(props);

        this.state = {
            checkIfLoggedIn: false,
            isLoggedIn: null,
            firstName: localStorage.getItem("firstname"),
            lastName: localStorage.getItem("lastname"),
            friends: localStorage.getItem("friends"),
            userId: localStorage.getItem("userid")
        }

        this.logout = this.logout.bind(this);
    }

    componentDidMount(){
        fetch("http://localhost:3001/checkifloggedin",
            {
                method: "POST",
                credentials: "include"
            })
            .then(response=> response.json())
            .then(body => {
                if (body.isLoggedIn){
                    this.setState({
                        checkIfLoggedIn: true, 
                        isLoggedIn: true, 
                        firstName: localStorage.getItem("firstname"),
                        lastName: localStorage.getItem("lastname"),
                        friends: localStorage.getItem("friends"),
                        userId: localStorage.getItem("userid")
                    })
                }
                else{
                    this.setState({checkIfLoggedIn: true, isLoggedIn: false})
                }
            });
    }

    logout(event){
        event.preventDefault();
        const cookies = new Cookies();
        cookies.remove("authtoken");

        localStorage.removeItem("firstname");
        localStorage.removeItem("lastname");
        localStorage.removeItem("friends");
        localStorage.removeItem("userid");

        this.setState({isLoggedIn: false})
    }

    render(){
        if(!this.state.checkIfLoggedIn){
            return(<div>Please wait</div>)
        }
        else {
            if(this.state.isLoggedIn){
                console.log(this.state)
                return(
                <div>
                    <div className='head'>
                        <div>
                            <img src='/logo.png' className='logo' alt='page logo'/>
                        </div>
                        <div>
                            <p className='logoname'>Calamansee</p>
                        </div>
                        <div className="link">
                        <p>Hi {this.state.firstName} {this.state.lastName}</p>
                        </div>
                        <div className="link">
                            <button id="logout" onClick={this.logout}>Log Out</button>
                        </div>
                    </div>
                    <FeedBody />
                </div>
                );
            }
            else{
                return(<Navigate to="/log-in" />)
            }
        }
    }
}

export default Feed;