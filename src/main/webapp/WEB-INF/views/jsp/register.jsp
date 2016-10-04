<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.codetube.model.user.IUser"%>
<%@ page session="false" %>			
			
				<div class="signin">
					<a href="#small-dialog3" class="play-icon popup-with-zoom-anim">Sign
						Up</a>
					<!-- pop-up-box -->
					<script type="text/javascript" src="js/modernizr.custom.min.js"></script>
					<link href="css/popuo-box.css" rel="stylesheet" type="text/css"
						media="all" />
					<script src="js/jquery.magnific-popup.js" type="text/javascript"></script>
					<!--//pop-up-box -->

					<div id="small-dialog3" class="mfp-hide">
						<h3>Create Account</h3>

						<div class="signup">
							<form role="form" method="POST" action="./Register">
								<input name="email" type="email" class="email"
									placeholder="Email" maxlength="30" required="required"
									pattern=".{<%=IUser.MIN_EMAIL_LENGTH%>,<%=IUser.MAX_FIELD_LENGTH%>}"
									required
										<%-- title="<%=IUser.MIN_EMAIL_LENGTH%> to <%=IUser.MAX_FIELD_LENGTH%> characters" /> --%>
										title="Enter a valid email" />
								<input name="password" type="password" placeholder="Password"
									maxlength="30" required="required"
									pattern=".{<%=IUser.MIN_PASSWORD_LENGTH%>,<%=IUser.MAX_FIELD_LENGTH%>}"
									required
									title="<%=IUser.MIN_PASSWORD_LENGTH%> to <%=IUser.MAX_FIELD_LENGTH%> characters"
									autocomplete="off" /> <input name="user_name" type="text"
									class="email" placeholder="Username" maxlength="30"
									required="required"
									pattern=".{<%=IUser.MIN_NAME_LENGTH%>,<%=IUser.MAX_FIELD_LENGTH%>}"
									required
									title="<%=IUser.MIN_NAME_LENGTH%> to <%=IUser.MAX_FIELD_LENGTH%> characters" />
								<input name="first_name" type="text" class="email"
									placeholder="First name" maxlength="30" required="required"
									pattern=".{<%=IUser.MIN_NAME_LENGTH%>,<%=IUser.MAX_FIELD_LENGTH%>}"
									required
									title="<%=IUser.MIN_NAME_LENGTH%> to <%=IUser.MAX_FIELD_LENGTH%> characters" />
								<input name="last_name" type="text" class="email"
									placeholder="Last name" maxlength="30" required="required"
									pattern=".{<%=IUser.MIN_NAME_LENGTH%>,<%=IUser.MAX_FIELD_LENGTH%>}"
									required
									title="<%=IUser.MIN_NAME_LENGTH%> to <%=IUser.MAX_FIELD_LENGTH%> characters" />
								<input type="submit" value="Sign Up" />
							</form>
							<div class="button-bottom">
								<p>
									Already have an account? <a href="#small-dialog"
										class="play-icon popup-with-zoom-anim">Login</a>
								</p>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div id="small-dialog7" class="mfp-hide">
						<h3>Create Account</h3>
						<div class="social-sits">
							<div class="facebook-button">
								<a href="#">Connect with Facebook</a>
							</div>
							<div class="chrome-button">
								<a href="#">Connect with Google</a>
							</div>
							<div class="button-bottom">
								<p>
									Already have an account? <a href="#small-dialog"
										class="play-icon popup-with-zoom-anim">Login</a>
								</p>
							</div>
						</div>
						<div class="signup">
							<form action="upload.html">
								<input type="text" class="email" placeholder="Email"
									required="required" pattern="([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?"
									title="Enter a valid email" /> <input type="password"
									placeholder="Password" required="required" pattern=".{6,}"
									title="Minimum 6 characters required" autocomplete="off" /> <input
									type="submit" value="Sign In" />
							</form>
						</div>
						<div class="clearfix"></div>
					</div>
					<div id="small-dialog4" class="mfp-hide">
						<h3>Feedback</h3>
						<div class="feedback-grids">
							<div class="feedback-grid">
								<p>Suspendisse tristique magna ut urna pellentesque, ut
									egestas velit faucibus. Nullam mattis lectus ullamcorper dui
									dignissim, sit amet egestas orci ullamcorper.</p>
							</div>
							<div class="button-bottom">
								<p>
									<a href="#small-dialog" class="play-icon popup-with-zoom-anim">Sign
										in</a> to get started.
								</p>
							</div>
						</div>
					</div>
					<div id="small-dialog5" class="mfp-hide">
						<h3>Help</h3>
						<div class="help-grid">
							<p>Suspendisse tristique magna ut urna pellentesque, ut
								egestas velit faucibus. Nullam mattis lectus ullamcorper dui
								dignissim, sit amet egestas orci ullamcorper.</p>
						</div>
						<div class="help-grids">
							<div class="help-button-bottom">
								<p>
									<a href="#small-dialog4" class="play-icon popup-with-zoom-anim">Feedback</a>
								</p>
							</div>
							<div class="help-button-bottom">
								<p>
									<a href="#small-dialog6" class="play-icon popup-with-zoom-anim">Lorem
										ipsum dolor sit amet</a>
								</p>
							</div>
							<div class="help-button-bottom">
								<p>
									<a href="#small-dialog6" class="play-icon popup-with-zoom-anim">Nunc
										vitae rutrum enim</a>
								</p>
							</div>
							<div class="help-button-bottom">
								<p>
									<a href="#small-dialog6" class="play-icon popup-with-zoom-anim">Mauris
										at volutpat leo</a>
								</p>
							</div>
							<div class="help-button-bottom">
								<p>
									<a href="#small-dialog6" class="play-icon popup-with-zoom-anim">Mauris
										vehicula rutrum velit</a>
								</p>
							</div>
							<div class="help-button-bottom">
								<p>
									<a href="#small-dialog6" class="play-icon popup-with-zoom-anim">Aliquam
										eget ante non orci fac</a>
								</p>
							</div>
						</div>
					</div>
					<div id="small-dialog6" class="mfp-hide">
						<div class="video-information-text">
							<h4>Video information & settings</h4>
							<p>Suspendisse tristique magna ut urna pellentesque, ut
								egestas velit faucibus. Nullam mattis lectus ullamcorper dui
								dignissim, sit amet egestas orci ullamcorper.</p>
							<ol>
								<li>Nunc vitae rutrum enim. Mauris at volutpat leo. Vivamus
									dapibus mi ut elit fermentum tincidunt.</li>
								<li>Nunc vitae rutrum enim. Mauris at volutpat leo. Vivamus
									dapibus mi ut elit fermentum tincidunt.</li>
								<li>Nunc vitae rutrum enim. Mauris at volutpat leo. Vivamus
									dapibus mi ut elit fermentum tincidunt.</li>
								<li>Nunc vitae rutrum enim. Mauris at volutpat leo. Vivamus
									dapibus mi ut elit fermentum tincidunt.</li>
								<li>Nunc vitae rutrum enim. Mauris at volutpat leo. Vivamus
									dapibus mi ut elit fermentum tincidunt.</li>
							</ol>
						</div>
					</div>
					<script>
						$(document).ready(function() {
							$('.popup-with-zoom-anim').magnificPopup({
								type : 'inline',
								fixedContentPos : false,
								fixedBgPos : true,
								overflowY : 'auto',
								closeBtnInside : true,
								preloader : false,
								midClick : true,
								removalDelay : 300,
								mainClass : 'my-mfp-zoom-in'
							});

						});
					</script>
				</div>
