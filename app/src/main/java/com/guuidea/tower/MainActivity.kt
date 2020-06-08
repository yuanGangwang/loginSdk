package com.guuidea.tower

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.guuidea.towersdk.LoginResult
import com.guuidea.towersdk.TowerLogin
import com.guuidea.towersdk.activity.ChangePwdActivity
import com.guuidea.towersdk.net.CallBackUtil
import com.guuidea.towersdk.net.HeaderManager
import com.guuidea.towersdk.net.UrlHttpUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    var appkey = "8089c1b7821304f8c993c8b1c8f21350"
    val appSecret = "ODxYzhmMjEzNTA="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login.setOnClickListener {
            TowerLogin.getInstance().startLoginAuth(
                MainActivity@ this,
                appkey,
                object : LoginResult {
                    override fun onSuccess(authToken: String) {
                        addMsg("login  success   token = $authToken")
                        requestUserScerte(authToken)
                    }

                    override fun onCancel() {
                        show()
                        addMsg("login cancel")

                    }

                    override fun onError(throwable: Throwable?) {
                        addMsg(throwable?.message + "")
                    }
                })
        }

        changePwd.setOnClickListener {
            startActivity(Intent(MainActivity@ this, ChangePwdActivity::class.java))
        }
    }

    private fun requestUserScerte(authToken: String) {
        addMsg("获取用户授权临时凭证")
        val jsonObject = JSONObject()
        jsonObject.put("appKey", appkey)
        jsonObject.put("state", 1)
        jsonObject.put("responseType", "CODE")

        val params =
            HashMap<String, String>()
        params["appKey"] = appkey
        params["authToken"] = authToken

        UrlHttpUtil.postJson(url, jsonObject.toString(), params, object : CallBackUtil() {
            override fun onFailure(throwable: Throwable?) {
                addMsg("获取凭证失败" + throwable?.message)
            }

            override fun onResponse(response: com.google.gson.JsonObject?) {
                addMsg("获取凭证成功" + response?.get("data")?.asString)
                getToken(response?.get("data")?.asString)
            }
        })
    }

    private fun getToken(code: String?) {
        val params = HashMap<String, String>()
        params["appKey"] = appkey
        params["appSecret"] = appSecret
        params["code"] = code!!
        UrlHttpUtil.post(
            url2, params
            , HeaderManager.makeHeader(), object : CallBackUtil() {
                override fun onFailure(throwable: Throwable?) {
                    addMsg("获取用户授权码失败" + throwable?.message)
                }

                override fun onResponse(response: com.google.gson.JsonObject?) {
                    addMsg("获取用户授权码成功")
                    getUserInfo(Gson().fromJson(response, Token::class.java))
                }
            }
        )
    }

    private fun getUserInfo(token: Token) {
        val jsonObject = JSONObject()
        jsonObject.put("appKey", appkey)

        val params =
            HashMap<String, String>()
        params["appKey"] = appkey
        params["accessToken"] = token.data.accessToken

        UrlHttpUtil.postJson(
            url3, jsonObject.toString()
            , params, object : CallBackUtil() {
                override fun onFailure(throwable: Throwable?) {
                    addMsg("获取用户信息失败" + throwable?.message)
                }

                override fun onResponse(response: com.google.gson.JsonObject?) {
                    addMsg("获取用户信息成功" + Gson().fromJson(response, UserInfo::class.java))
                }
            }
        )
    }


    fun show() {
        Toast.makeText(MainActivity@ this, "取消登录", Toast.LENGTH_SHORT).show()
    }

    fun addMsg(info: String) {
        msg.setText(msg.text.toString() + "\n" + info)
    }

    //用户临时授权凭证
    val url = "http://190.1.1.241:5002/api/oauth/code"

    //获取用户授权码-对外
    val url2 = "http://190.1.1.241:5002/api/oauth/access/token"

    //获取用户信息
    val url3 = "http://190.1.1.241:5002/api/user/app/account/info"
}