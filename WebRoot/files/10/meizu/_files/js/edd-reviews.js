/**
 * Reviews JS
 */
if (typeof (jQuery) != 'undefined') {

	(function( $ ) {
		"use strict";

		$(function() {

			var EDD_Reviews = {
				init: function () {
					this.show();
					this.remove();
					this.ratings();
					this.votes();
				},

				show: function() {
					$('.edd-review-vote').show();
				},

				remove: function () {
					$('.edd_show_if_no_js').remove();
				},

				ratings: function () {
					$('.comment_form_rating .edd_reviews_rating_box').find('a').on('click', function () {
						$(this).parent().prev('.edd_star_rating').width(92 * (5 - $(this).prevAll('a').length) / 5);
						$('input#edd_rating').val($(this).attr('data-rating'));
						return false;
					});
				},

				votes: function() {
					$('span.edd-reviews-voting-buttons a').on('click', function() {
						var $this = $(this),
							vote = $this.data('edd-reviews-vote'),
							data = {
								action: 'edd_reviews_process_vote',
								security: edd_reviews_params.edd_voting_nonce,
								review_vote: vote,
								comment_id: $this.data('edd-reviews-comment-id'),
								edd_reviews_ajax: true
							};

						$this.parent().after('<img src="' + edd_scripts.ajax_loader + '" class="edd-reviews-vote-ajax" />');

						$.post( edd_reviews_params.ajax_url, data, function (response) {
							if (response == 'success') {
								$this.parent().parent().parent().addClass('edd-yellowfade').html('<p style="margin:0;padding:0;">' + edd_reviews_params.thank_you_msg + '</p>');
								$('.edd-reviews-vote-ajax').remove();
							}
						});

						return false;
					});
				}
			}

			EDD_Reviews.init();

		});

	}(jQuery));

}