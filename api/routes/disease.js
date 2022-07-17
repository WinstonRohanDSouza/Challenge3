const express = require("express")
const router = express.Router()
const axios= require("axios")
const fs = require('fs');
//const { Auth, LoginCredentials } = require("two-step-auth");
//var nodemailer = require('nodemailer');


let cough={"cough":{
    "How Long":["1 week","Two Week","More than two week"],
    "Type of Cough":["Dry Cough","Cough With Discharge"],
    "If discharge,type of discharge":["Watery and colorless Thick","greenish white mucous like black color discharge"],
}}
let fever={"fever":
{
    "How Long":["1-2 days","3-6 days","More than a week"],
    "Temperature":["below 100F","100-102F","more than 102F","Not fixed"],
    "Fever Characteristics":["Continuous fever ","Relapsing fever","Intermittent Fever","Remittent Fever"],

}}
let Headache={"headache":{
    "How Long":["1-2 days","A week","Regularly for more than a week",],
    "Type of Headache":["Throbbing pain","Dull constant pain","Intense piercing pain"],
    "Site of Headache":["Throbbing pain in one side","Throbbing pain throughout head","Intense piercing pain around eyes","Dull throbbing pain around eyes,cheeks,forehead"],
    "Any other symptoms":["Seeing flickering/zig-zagging lines/lights","Nausea and Vomiting High Blood Pressure","Restlessness, anxiety","Low Blood Pressure","Running/blocked nose"]

}}
let looseStool={"loosStool":{
    "How Long":["1-2 days","A week","more than a week",],
    "Type of motions":["Watery stool without mucous discharge","White mucoid discharge with or without watery stool","Mucous with blood","Loose motions with blood","Loose motions with blood dicharge without mucous"],
    "Frequency of motions ":["3-4 times in a day","5 or more times in a day"],
    "Any other symptoms ":["Abdominal cramps/pain","Nausea and Vomiting","bloating","fever","dark urine"]
}}
let soreThroat={"sore throat":{
    "How Long":["1-2 days","A week","more than a week",],
    "Type of sore throat ":["Pain or a scratchy sensation in the throat","Difficulty swallowing ","Sore, swollen glands in neck or jaw","Swollen, red tonsils","White patches or pus on tonsils","Hoarse or muffled voice."],
    "Any other symptoms":["Fever","Running/blocked nose","Headache","Cough","Bad breath"]
}}
let jointPain={"jointPain":{
    "How Long":["1-5 days","1-2 week","more than a month",],
    "Type of Joint pain":["Chronic pain, remains all the time",
    "Pain on standing/walking, gets relieved on rest","Morning time pain only, relieves after sometime","Seasonal pain, exacerbates during cold weather/staying in AC room"],
    "Site of Joint Pain":["Knee joint pain only","Fingers and toe joints only","Multiple joint pain at once","Running pain from one joint to another","Posterior surface of knee joint"],
    "Any other symptoms":["Swelling of joints","Redness of Joints","Fever","Stiffness","Loss of range of motion of joint","Numbness","Tingling sensation in joints"]

}}
let dyspnea={"dyspnea":{
    "How Long":["1-2 week","More than 2 weeks","Seasonal/during winters ",],
    "Type of breathlessness":["On doing strenous activity","On doing regular activities","During resting state","Along with cough"],
    "Any other symptoms":["Wheezing sound","Chest Pain","Cold clammy skin","Blueish discoloration of lips","Tightness around chest"],
    
}}
let irregularPeriods={"irregualrPeriods":{
    "How Long":["1-2 cycle only ","several months","intermittently"],
    "Type of Irregular mentruation":["prolonged menstrual ","bleeding period ","Short bleeding period","No bleeding","Continuous bleeding all month"],
    "Any other symptoms ":["Lower abdominal Pain during menstruation","Lower back pain ","Nausea","Dizziness","Pale skin, puffy face ","Constipation","Weight gain "]
}}
let skin={"skin rash":{
    "How Long":["1-5 days","1-2 week","More Than a month",],
    "Type of Skin Rashes":["Small (1-2mm) solid skin ","Large pus filled skin eruptions"]

}}
let vision={"abnormalVision":{
    "How Long":["1-5 days","1-2 week","More Than a month"],
    "Type of blurred vision ":["Clouded or dimmed vision","Sensitivity to light and glare","Seeing halos around light","Fading or yellowing of colours"],
    "Any other symptoms":["Frequent change of eye power"]
}}
//sending disease data
router.post("/get",async (req,res)=>
{
    
    let diseases=(req.body)
    
    if(diseases!={})
    {
        alldiseases = {}
        diseases.forEach((disease)=>{
            disease = disease.toLowerCase()
                       
        if(disease=="cough" || disease=="COUGH"){
            alldiseases["cough"] =cough
        }
        if(disease=="fever" || disease=="FEVER"){
            alldiseases["fever" ] =fever
        }
        if(disease=="headache" || disease=="HEADACHE"){
            alldiseases["headache" ] =Headache
        }
        if(disease=="loose stool" || disease=="LOOSE STOOL"){
            alldiseases["loose stool"] =looseStool
        }
        if(disease=="sore throat" || disease=="SORE THROAT"){
            alldiseases["sore throat"] =soreThroat
            // res.send(soreThroat)
        }
        if(disease=="joint pain" || disease=="JOINT PAIN"){
            alldiseases["jointPain"] =jointPain
            // res.send(joinstPain)
        }
        if(disease=="skin rash" || disease=="SKIN RASH"){
            alldiseases["skin rash"] =skin
        }
        // if(disease=="dyspnea" || disease=="breathlessbess"){
        //     res.send(dyspnea)
        // }
        // if(disease=="irregular periods" || disease=="menstrual bleeding"){
        //     res.send(irregularPeriods)
        // }
        // if(disease=="skin rashes" || disease=="Eruption of skin "){
        //     // res.send(skin)
        // }
        // if(disease=="blurred vision" || disease=="abnormal vision"){
        //     // res.send(vision)
        // }
        })

        res.send(alldiseases)

    }
    else
    {
        res.send({})
    }
   // res.send("on disease")
    console.log(req.body.disease)
})

router.get("/download",async (req,res)=>
{
    res.download("C://Users//SR096079//Downloads//certificate.pdf");
})
router.get("/data",async (req,res)=>
{
    
    let data= await axios.get("https://fhir-open.cerner.com/dstu2/ec2458f2-1e24-41c8-b71b-0e701af7583d/MedicationStatement?patient=12724066")
    results= data.data.entry[0].resource.text.div
    let rawdata = fs.readFileSync('sample.json');
    let check = JSON.parse(rawdata);
    //let text = JSON.stringify(results)
    //text=JSON.parse(text)
    res.send({"medications":{"source 1":check.data.medications,"source 2":results}})
})
module.exports=router