import { SpotifyAngularPage } from './app.po';

describe('spotify-angular App', () => {
  let page: SpotifyAngularPage;

  beforeEach(() => {
    page = new SpotifyAngularPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
