<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/component_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Components";
  include($root.'include/begin.inc.'.$phpEx);
?>

<div class="titlebar"><div><div><h2>Components</h2></div></div></div>

<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>ACT</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/act.gif" alt="ACT" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable</td> 
    </tr>
    </table>       
    <p>
	The ACT device displays ACT values versus time.
	</p>   
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Alarm Indicator</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/alarmIndicator.gif" alt="Alarm Indicator" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Not Clickable</td> 
    </tr>
    </table>       
    <p>
	The alarm indicator notices the current ECMO status to the user. (green as normal or red as alert)
	</p>   
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Bubble Detector</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/bubbleDetector.gif" alt="Bubble Detector" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable</td> 
    </tr>
    </table>       
    <p>
	The bubble detector uses to detect bubble in the tube.  User can click reset button when there is no bubble.
	</p>  
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>CDI Monitor</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/cdiMonitor.gif" alt="CDI Monitor" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Not Clickable</td> 
    </tr>
    </table>       
    <p>
	The CDI Monitor shows pH, pCO2, pO2, HCO3, temperature, BE, arterial O2 saturation, HCT, Hgb, and venous O2 saturation.
	</p>   
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Heater</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/heater.gif" alt="Heater" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable</td> 
    </tr>
    </table>       
    <p>
    The heater shows the temperature in centigrade.  The user can click +/- to increase/decrease the heater's temperature.
    </p> 
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Intervention</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/intervention.gif" alt="Intervention" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable</td> 
    </tr>
    </table>       
    <p>
    The interventions allow to inject medicine such as blood cells, platelets, FFP, Heparin Bolus, Dopamine, and/or 5% Albumin to the patient.  The user also can select one of the 3 positions to inject the medicine.
    </p>   
  </td>
  </tr>
</table>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Lab</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/lab.gif" alt="Lab" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable [ABG = Blood Gas Lab / CHEM = Chemistry Lab / HEME = Hematology Lab / IMG = Imaging Lab]</td> 
    </tr>
    </table>       
    <p>
    The lab contains 4 different lab results as following: ABG (Blood Gas Lab), CHEM (Chemistry Lab), HEME (Hematology Lab), and IMG (Imaging Lab).
      ABG, CHEM, HEME displays the lab value versus trial.  IMG displays images such as Chest X-Ray.
    </p>   
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Oxigenator</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/oxigenator.gif" alt="Oxigenator" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable (Silicon | PMP)</td> 
    </tr>
    </table>       
    <p>
    The oxigenator adjusts O2, CO2, and oxygen concentration in percent.  The user can select Silicon or PMP.
    </p>  
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Patient</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/patient.gif" alt="Patient" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable</td> 
    </tr>
    </table>       
    <p>
    The patient allows the user making several actions to the patient such as check canula site, check for bleeding, check ETT, suction ETT, check diaper, and stimulate baby.
    </p>  
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Physiologic Monitor</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/physiologicMonitor.gif" alt="Physiologic Monitor" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Not Clickable</td> 
    </tr>
    </table>       
    <p>
    The physiologic monitor displays information such as heart rate, O2 saturation in percent, systolic blood pressure in mmHg, diastolic blood pressure in mmHg.
    </p>   
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Pressure Monitor</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/pressureMonitor.gif" alt="Pressure Monitor" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable</td> 
    </tr>
    </table>       
    <p>
    The pressure monitor shows the pre-membrane, post-membrane, and venous pressure in torr.  The user also can adjust
       pre-membrane, post-membrane, and venous pressure minimum/maximum by clicking +/-
    </p>   
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Pump</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/pump.gif" alt="Pump" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable (Centrifugal | Roller)</td> 
    </tr>
    </table>       
    <p>
    The pump shows the flow in liters per minute. The user can select either centrifugal pump or roller pump.  The user also can turn on/off the pump and adjust flow of the pump.
    </p>  
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Tube</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/tube.gif" alt="Tube" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Clickable [Clamp], Not Clickable (VA Mode | VV Mode)</td> 
    </tr>
    </table>       
    <p>
    The tube allows flow between the patient and ECMO machine.  The user allows to choose either VA Mode or VV Mode to the patient.  The user also allows to use clamp on the tube.
    </p>    
  </td>
  </tr>
</table>
</div>
<br>
<div class="gamebar"></div>
<div class="gametitle">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Ventilator</td>
  </tr>
</table>
</div>
<div class="gameinfo">
<table border="0" cellspacing="0" cellpadding="0">  
  <tr>    
  <td width="120" height="100" valign="top">
    <div class="gamethumbnail"><img src="image/components/ventilator.gif" alt="Ventilator" width="120" height="100" class="main" />
    <img class="frame" src="image/interface/frame.gif"></div>        
  </td>
  <td width="100%"  valign="top" style="padding-left: 2px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="1" style="border-bottom: 1px solid #a0a0a0;">
    <tr>       
      <td class="category">Not Clickable (Conventional | High Frequency)</td> 
    </tr>
    </table>       
    <p>
    The ventilator displays pressure information.  The user can select conventional ventilator: peak inspiratory pressure (PIP), 
    peak end expiratory pressure (PEEP), mean pressure (MAP), rate, and oxygen conzentration in percent or 
    high frequency ventilator: amplitude, hertz value, mean pressure, and oxygen conzentration in percent.
    </p>   
  </td>
  </tr>
</table>
</div>
</div>
<?php
  include($root.'include/end.inc.'.$phpEx);
?>
