
package com.whcst;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.4
 * 2018-09-28T14:56:44.723+08:00
 * Generated source version: 3.1.4
 * 
 */
public final class SiteWebserviceSoap_SiteWebserviceSoap_Client {

    private static final QName SERVICE_NAME = new QName("http://www.whcst.com/", "SiteWebservice");

    private SiteWebserviceSoap_SiteWebserviceSoap_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SiteWebservice.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        SiteWebservice ss = new SiteWebservice(wsdlURL, SERVICE_NAME);
        SiteWebserviceSoap port = ss.getSiteWebserviceSoap(); 
        
        System.out.println("Invoking sendCode...");
        java.lang.String _sendCode_phone = "15601838927";
        java.lang.String _sendCode_codetype = "1";
        java.lang.String _sendCode__return = port.sendCode(_sendCode_phone, _sendCode_codetype);
        System.out.println("sendCode.result=" + _sendCode__return);
        
//        {
//        System.out.println("Invoking cardDepositInfo...");
//        java.lang.String _cardDepositInfo_code = "";
//        java.lang.String _cardDepositInfo_cardno = "";
//        java.lang.String _cardDepositInfo__return = port.cardDepositInfo(_cardDepositInfo_code, _cardDepositInfo_cardno);
//        System.out.println("cardDepositInfo.result=" + _cardDepositInfo__return);
//
//
//        }
//        {
//        System.out.println("Invoking cardBind...");
//        java.lang.String _cardBind_mastercardno = "";
//        java.lang.String _cardBind_childcardno = "";
//        java.lang.String _cardBind_passwd = "";
//        java.lang.String _cardBind_unitid = "";
//        java.lang.String _cardBind__return = port.cardBind(_cardBind_mastercardno, _cardBind_childcardno, _cardBind_passwd, _cardBind_unitid);
//        System.out.println("cardBind.result=" + _cardBind__return);
//
//
//        }
//        {
//        System.out.println("Invoking resetPasswd...");
//        java.lang.String _resetPasswd_phone = "";
//        java.lang.String _resetPasswd_newpasswd = "";
//        java.lang.String _resetPasswd_passwdtype = "";
//        java.lang.String _resetPasswd_unitid = "";
//        java.lang.String _resetPasswd__return = port.resetPasswd(_resetPasswd_phone, _resetPasswd_newpasswd, _resetPasswd_passwdtype, _resetPasswd_unitid);
//        System.out.println("resetPasswd.result=" + _resetPasswd__return);
//
//
//        }
//        {
//        System.out.println("Invoking cardTrade...");
//        java.lang.String _cardTrade_cardno = "";
//        java.lang.String _cardTrade_code = "";
//        java.lang.String _cardTrade__return = port.cardTrade(_cardTrade_cardno, _cardTrade_code);
//        System.out.println("cardTrade.result=" + _cardTrade__return);
//
//
//        }
//        {
//        System.out.println("Invoking sendCode...");
//        java.lang.String _sendCode_phone = "";
//        java.lang.String _sendCode_codetype = "";
//        java.lang.String _sendCode__return = port.sendCode(_sendCode_phone, _sendCode_codetype);
//        System.out.println("sendCode.result=" + _sendCode__return);
//
//
//        }
//        {
//        System.out.println("Invoking accLogin...");
//        java.lang.String _accLogin_accountno = "";
//        java.lang.String _accLogin_passwd = "";
//        java.lang.String _accLogin__return = port.accLogin(_accLogin_accountno, _accLogin_passwd);
//        System.out.println("accLogin.result=" + _accLogin__return);
//
//
//        }
//        {
//        System.out.println("Invoking accOpen...");
//        java.lang.String _accOpen_cardno = "";
//        java.lang.String _accOpen_txnpasswd = "";
//        java.lang.String _accOpen_qrypasswd = "";
//        java.lang.String _accOpen_name = "";
//        java.lang.String _accOpen_idtype = "";
//        java.lang.String _accOpen_idcardno = "";
//        java.lang.String _accOpen_phone = "";
//        java.lang.String _accOpen_unitid = "";
//        java.lang.String _accOpen__return = port.accOpen(_accOpen_cardno, _accOpen_txnpasswd, _accOpen_qrypasswd, _accOpen_name, _accOpen_idtype, _accOpen_idcardno, _accOpen_phone, _accOpen_unitid);
//        System.out.println("accOpen.result=" + _accOpen__return);
//
//
//        }
//        {
//        System.out.println("Invoking appLoginUrl...");
//        java.lang.String _appLoginUrl_phone = "";
//        java.lang.String _appLoginUrl_remoteip = "";
//        java.lang.String _appLoginUrl_unitid = "";
//        java.lang.String _appLoginUrl__return = port.appLoginUrl(_appLoginUrl_phone, _appLoginUrl_remoteip, _appLoginUrl_unitid);
//        System.out.println("appLoginUrl.result=" + _appLoginUrl__return);
//
//
//        }
//        {
//        System.out.println("Invoking checkTxnPasswd...");
//        java.lang.String _checkTxnPasswd_phone = "";
//        java.lang.String _checkTxnPasswd__return = port.checkTxnPasswd(_checkTxnPasswd_phone);
//        System.out.println("checkTxnPasswd.result=" + _checkTxnPasswd__return);
//
//
//        }
//        {
//        System.out.println("Invoking accTrade...");
//        java.lang.String _accTrade_cardno = "";
//        java.lang.String _accTrade_code = "";
//        java.lang.String _accTrade__return = port.accTrade(_accTrade_cardno, _accTrade_code);
//        System.out.println("accTrade.result=" + _accTrade__return);
//
//
//        }
//        {
//        System.out.println("Invoking cardUnBind...");
//        java.lang.String _cardUnBind_mastercardno = "";
//        java.lang.String _cardUnBind_childcardno = "";
//        java.lang.String _cardUnBind_passwd = "";
//        java.lang.String _cardUnBind_unitid = "";
//        java.lang.String _cardUnBind__return = port.cardUnBind(_cardUnBind_mastercardno, _cardUnBind_childcardno, _cardUnBind_passwd, _cardUnBind_unitid);
//        System.out.println("cardUnBind.result=" + _cardUnBind__return);
//
//
//        }
//        {
//        System.out.println("Invoking managePasswd...");
//        java.lang.String _managePasswd_cardno = "";
//        java.lang.String _managePasswd_oldpasswd = "";
//        java.lang.String _managePasswd_newpasswd = "";
//        java.lang.String _managePasswd_passwdtype = "";
//        java.lang.String _managePasswd_idtype = "";
//        java.lang.String _managePasswd_idcardno = "";
//        java.lang.String _managePasswd_unitid = "";
//        java.lang.String _managePasswd__return = port.managePasswd(_managePasswd_cardno, _managePasswd_oldpasswd, _managePasswd_newpasswd, _managePasswd_passwdtype, _managePasswd_idtype, _managePasswd_idcardno, _managePasswd_unitid);
//        System.out.println("managePasswd.result=" + _managePasswd__return);
//
//
//        }
//        {
//        System.out.println("Invoking accInfo...");
//        java.lang.String _accInfo_phone = "";
//        java.lang.String _accInfo_code = "";
//        java.lang.String _accInfo__return = port.accInfo(_accInfo_phone, _accInfo_code);
//        System.out.println("accInfo.result=" + _accInfo__return);
//
//
//        }
//        {
//        System.out.println("Invoking cardBindSearch...");
//        java.lang.String _cardBindSearch_mastercardno = "";
//        java.lang.String _cardBindSearch_code = "";
//        java.lang.String _cardBindSearch__return = port.cardBindSearch(_cardBindSearch_mastercardno, _cardBindSearch_code);
//        System.out.println("cardBindSearch.result=" + _cardBindSearch__return);
//
//
//        }
//        {
//        System.out.println("Invoking webLoginUrl...");
//        java.lang.String _webLoginUrl_phone = "";
//        java.lang.String _webLoginUrl_remoteip = "";
//        java.lang.String _webLoginUrl_unitid = "";
//        java.lang.String _webLoginUrl__return = port.webLoginUrl(_webLoginUrl_phone, _webLoginUrl_remoteip, _webLoginUrl_unitid);
//        System.out.println("webLoginUrl.result=" + _webLoginUrl__return);
//
//
//        }
//        {
//        System.out.println("Invoking decode...");
//        java.lang.String _decode_text = "";
//        java.lang.String _decode_key = "";
//        java.lang.String _decode__return = port.decode(_decode_text, _decode_key);
//        System.out.println("decode.result=" + _decode__return);
//
//
//        }

        System.exit(0);
    }

}
