import { TestBed } from '@angular/core/testing';

import { ChargesParTypeBulletin } from './charges-par-type-bulletin';

describe('ChargesParTypeBulletin', () => {
  let service: ChargesParTypeBulletin;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChargesParTypeBulletin);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
