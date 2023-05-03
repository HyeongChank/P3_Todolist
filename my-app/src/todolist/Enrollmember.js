import { useState } from "react";

const Enrollmember = () => {
    const [memberid, setMemberid] = useState();
    const [memberpw, setMemberpw] = useState();

    const enrollmembers = async(event) =>{
        event.preventDefault();
        try {
          const response = await fetch("http://localhost:8080/api/insertMembers", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ 
                id: memberid,
                password: memberpw,
              }),
          });
          const data = await response.json();
          console.log(data);
          // 받아온 데이터를 화면에 출력하는 코드 작성
        } catch (error) {
          console.error(error);
        }
    }
    const getMember = (e) =>{
        if (e.target.name === "id") {
            setMemberid(e.target.value);
          } else if (e.target.name === "password") {
            setMemberpw(e.target.value);
          }        
    }
    return(
        <div>
            <form onSubmit={enrollmembers}>
            <label>
                <input type="text" name="id" onChange={getMember}></input>
            </label>
            <label>
                <input type="password" name="password" onChange={getMember}></input>
            </label>
                <button type="submit">회원가입</button>
            </form>    
        </div>
    )
}
export default Enrollmember;



