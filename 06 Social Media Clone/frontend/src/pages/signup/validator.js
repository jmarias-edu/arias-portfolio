//Password validator from exercise 2

export function validatePassword(pass){
    if(pass.length<8){ //checks if password is at least 8 characters, returns false if less than
        return false;
    }

    var upper = false;
    var lower = false;
    var number = false;

    for(let x=0; x<pass.length;x++){ //for loop to check every character for all three character types
        if(!isNaN(pass[x])){
            number = true;
        }
        else if(pass[x]===pass[x].toLowerCase()){
            lower = true;
        }
        else if(pass[x]===pass[x].toUpperCase()){
            upper = true;
        }
    }

    if(!upper||!lower||!number){
        return false;
    }

    return true;
}

export function validateRepeatPassword(pass1, pass2){
    if(pass1===pass2){
        return true
    } else { return false }
}
