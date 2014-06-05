<!-- BODY end -->
          </td></tr></table>
        </td>
        </tr></table>
          </td></tr>
    </table>
      </td>
    <td style="background: #EEEEEE" width="6"><img src="image/interface/fade-right-top.gif" width="6" height="240"></td>
  </tr></table></tr>

  <tr height="46"> 
    <td style="background-image: url(image/interface/footer.jpg);" width="960" height="46">
  
<!-- FOOTER start -->
<div class="footer">
  <div class="footermenu">
    <a href="index.php">Home</a> | <a href="demo.php">Demo</a> | <a href="screenshot.php">Screenshots</a> | <a href="download.php">Download</a> | <a href="faq.php">FAQ</a> | <a href="about.php">About</a>
  </div>
  <div class="copyright">
    <a href="http://ecmojo.sourceforge.net/">ECMOjo</a>, Copyright &copy 2008-2009 by <a href="http://www.tri.jabsom.hawaii.edu/">Telehealth Research Institute</a>, <a href="http://www.hawaii.edu/">University of Hawaii</a>, Version <?php echo $system_version; ?>
  </div>
</div>
<!-- FOOTER end -->

<!-- SPOTLIGHT TRANSFER start -->
<script type="text/javascript">
  if ($("#spotlight").size() > 0) {
    $("#content_table").prepend("<tr height=\"158\"><td style=\"background-image: url(image/interface/spotlight.jpg); padding-left: 230px; padding-right: 10px; padding-top: 14px\" width=\"949\" height=\"158\">" + $("#spotlight").html() + "</td></tr>");
    $("#spotlight").remove();
  }
</script>
<!-- SPOTLIGHT TRANSFER end -->

  </td>
  </tr>
  <tr>
    <td style="background: #EEEEEE">
    &nbsp;
  </td>
  </tr>
</table>
</center>

</body>
</html>