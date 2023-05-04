import { useNavigate } from "react-router-dom";
const BLongin = (props) =>{
    const navigate = useNavigate();
    const loginbasic = async(e) =>{
        e.preventDefault();
        const data = {
            mid: e.target.id.value,
            password: e.target.password.value,
          };
        try{
            const response = await fetch('/api/login', {
                method:'POST',
                headers:{
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
                })
            

            if(!response.ok){
                throw new Error('login error');
            }
            const result = await response.text();
            if(result === '로그인 성공'){
                console.log('success');

                navigate('/p');                
            } else{
                console.log('failure');
            }
        } catch(error){
            console.error(error);
        }

    };       
    return(
        <div className="loginpage">
            <form onSubmit={loginbasic}>
                <label className="logindis">
                    <p className="loginp">ID :</p>
                    <input type="text" name="id" className="inputarea"></input>
                </label>
                <label className="logindis">
                    <p className="loginp">PASSWORD :</p>
                    <input type="password" name="password" className="inputarea"></input>
                </label>
                    <button type="submit">기사용자 입장</button>
            </form>
        </div>
    )
}
export default BLongin;