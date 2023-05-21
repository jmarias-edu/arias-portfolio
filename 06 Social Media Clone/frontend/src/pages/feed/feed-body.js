import Main from './main/Main.js'
import FriendsList from './sidebars/FriendsBar.js';
import Ads from './sidebars/AdsBar.js'
import './main/Header.css'
import React from 'react';

class FeedBody extends React.Component{

  render(){
    return (
      <div>
        <FriendsList/>
        <Main />
        <Ads />
      </div>
    );
  }
}

export default FeedBody;